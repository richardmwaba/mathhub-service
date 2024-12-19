package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.EnrolledClass;
import com.hubformath.mathhubservice.model.sis.EnrolledClassRequest;
import com.hubformath.mathhubservice.model.sis.EnrolledClassStatus;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.ClassRate;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.repository.sis.EnrolledClassRepository;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import com.hubformath.mathhubservice.service.systemconfig.ClassRateService;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
import com.hubformath.mathhubservice.util.StudentUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class EnrolledClassService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EnrolledClassService.class);

    private final EnrolledClassRepository enrolledClassRepository;

    private final StudentRepository studentRepository;

    private final ClassRateService classRateService;

    private final SubjectService subjectService;

    @Autowired
    public EnrolledClassService(EnrolledClassRepository enrolledClassRepository,
                                StudentRepository studentRepository, ClassRateService classRateService,
                                SubjectService subjectService) {
        this.enrolledClassRepository = enrolledClassRepository;
        this.studentRepository = studentRepository;
        this.classRateService = classRateService;
        this.subjectService = subjectService;
    }

    public EnrolledClass getEnrolledClassByClassId(String classId) {
        return enrolledClassRepository.findById(classId).orElseThrow();
    }

    public List<EnrolledClass> getAllClassesForStudent(String studentId, String enrolmentStatus) {
        List<EnrolledClass> allEnrolledClasses = getStudentById(studentId).getEnrolledClasses();

        return StringUtils.isNotEmpty(enrolmentStatus) ? allEnrolledClasses.stream()
                                                                           .filter(enrolledClass -> enrolmentStatus.equals(
                                                                                   enrolledClass.getEnrolmentStatus()
                                                                                                .getDescription()))
                                                                           .toList() : allEnrolledClasses;
    }

    public List<EnrolledClass> addClassesToStudent(String studentId, List<EnrolledClassRequest> enrolledClassRequests) {
        Student student = getStudentById(studentId);
        List<String> classesInWhichStudentIsEnrolled = classesInWhichStudentIsEnrolled(student, enrolledClassRequests);
        if (!classesInWhichStudentIsEnrolled.isEmpty()) {
            throw new IllegalStateException("Student is already enrolled in the following classes: " + classesInWhichStudentIsEnrolled);
        }
        List<EnrolledClass> newEnrolledClasses = enrolledClassRequests.stream()
                                                                      .map(this::getNewClass)
                                                                      .toList();

        student.getEnrolledClasses().addAll(newEnrolledClasses);
        student.setFinancialSummary(StudentUtil.computeStudentFinancialSummary(student));

        studentRepository.save(student);

        return student.getEnrolledClasses();
    }

    private List<String> classesInWhichStudentIsEnrolled(Student student,
                                                         List<EnrolledClassRequest> enrolledClassRequests) {
        return student.getEnrolledClasses()
                      .stream()
                      .filter(isActiveClass())
                      .filter(isAlreadyEnrolledInAtLeastOneClass(enrolledClassRequests))
                      .map(enrolledClass -> enrolledClass.getSubject().getName())
                      .toList();
    }

    private static Predicate<EnrolledClass> isActiveClass() {
        return enrolledClass -> enrolledClass.getEnrolmentStatus().equals(EnrolledClassStatus.ACTIVE);
    }

    private static Predicate<EnrolledClass> isAlreadyEnrolledInAtLeastOneClass(List<EnrolledClassRequest> enrolledClassRequests) {
        return enrolledClass -> enrolledClassRequests.stream()
                                                     .anyMatch(newClass -> newClass.subjectId()
                                                                                   .equals(enrolledClass.getSubject()
                                                                                                        .getId()));
    }

    private Student getStudentById(String studentId) {
        return studentRepository.findById(studentId).orElseThrow();
    }

    public EnrolledClass updateEnrolledClass(String enrolledClassId, EnrolledClassRequest enrolledClassRequest) {
        return enrolledClassRepository.findById(enrolledClassId)
                                      .map(enrolledClass -> {
                                          Optional.of(enrolledClassRequest.occurrence())
                                                  .ifPresent(enrolledClass::setOccurrence);
                                          Optional.ofNullable(enrolledClass.getStartDate())
                                                  .ifPresent(enrolledClass::setStartDate);
                                          Optional.of(enrolledClassRequest.duration())
                                                  .ifPresent(enrolledClass::setDuration);
                                          Optional.ofNullable(enrolledClassRequest.period())
                                                  .ifPresent(enrolledClass::setPeriod);
                                          Optional.ofNullable(enrolledClassRequest.sessionType())
                                                  .ifPresent(enrolledClass::setSessionType);
                                          enrolledClass.setEndDate(computeEndDate(enrolledClassRequest));
                                          return enrolledClassRepository.save(enrolledClass);
                                      }).orElseThrow();

    }

    public void cancelStudentsClass(String studentId, String classId) {
        EnrolledClass cancelledClass = enrolledClassRepository.findById(classId).orElseThrow();

        if (getStudentById(studentId) != null) {
            cancelledClass.setEnrolmentStatus(EnrolledClassStatus.CANCELLED);
            enrolledClassRepository.save(cancelledClass);
        }
    }

    @Scheduled(cron = "1 1 0 * * *") // Every day at 00:01:01
    @SuppressWarnings("unused") // This method is called by the scheduler
    public void updateEnrolledClassesStatus() {
        LOGGER.info("Updating enrolled classes' statuses...");
        List<EnrolledClass> activeClasses = enrolledClassRepository.findByEnrolmentStatus(EnrolledClassStatus.ACTIVE);
        activeClasses.stream()
                     .filter(enrolledClass -> LocalDate.now().isAfter(enrolledClass.getEndDate()))
                     .forEach(enrolledClass -> {
                         enrolledClass.setEnrolmentStatus(EnrolledClassStatus.COMPLETED);
                         enrolledClassRepository.save(enrolledClass);
                     });
        LOGGER.info("Completed update of enrolled classes' statuses.");
    }

    private EnrolledClass getNewClass(EnrolledClassRequest enrolledClassRequest) {
        Subject subject = subjectService.getSubjectById(enrolledClassRequest.subjectId());
        ClassRate classRate = classRateService.getClassRateBySubjectComplexity(subject.getComplexity());
        EnrolledClass newEnrolledClass = new EnrolledClass(subject,
                                                           enrolledClassRequest.occurrence(),
                                                           enrolledClassRequest.startDate(),
                                                           classRate.getAmount(),
                                                           enrolledClassRequest.duration(),
                                                           enrolledClassRequest.period(),
                                                           computeEndDate(enrolledClassRequest),
                                                           enrolledClassRequest.sessionType());
        newEnrolledClass.setPaymentStatus(PaymentStatus.UNPAID);
        newEnrolledClass.setEnrolmentStatus(EnrolledClassStatus.ACTIVE);
        return newEnrolledClass;
    }

    private LocalDate computeEndDate(EnrolledClassRequest enrolledClassRequest) {
        return switch (enrolledClassRequest.period()) {
            case DAYS -> enrolledClassRequest.startDate().plusDays(enrolledClassRequest.duration());
            case WEEKS -> enrolledClassRequest.startDate().plusWeeks(enrolledClassRequest.duration());
            case MONTHS -> enrolledClassRequest.startDate().plusMonths(enrolledClassRequest.duration());
        };
    }
}
