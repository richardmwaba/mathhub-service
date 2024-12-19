package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.EnrolledClass;
import com.hubformath.mathhubservice.model.sis.EnrolledClassRequest;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.ClassRate;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.repository.sis.EnrolledClassRepository;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import com.hubformath.mathhubservice.service.systemconfig.ClassRateService;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
import com.hubformath.mathhubservice.util.StudentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class EnrolledClassService {

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

    public EnrolledClass getStudentsClassByClassId(String studentId, String classId) {
        return getStudentById(studentId).getEnrolledClasses()
                                        .stream()
                                        .filter(aClass -> classId.equals(aClass.getId()))
                                        .findFirst()
                                        .orElseThrow();
    }

    public List<EnrolledClass> getAllClassesForStudent(String studentId, boolean onlyActiveClasses) {
        List<EnrolledClass> allEnrolledClasses = getStudentById(studentId).getEnrolledClasses();

        return onlyActiveClasses ? allEnrolledClasses.stream()
                                                     .filter(isActiveClass())
                                                     .toList() : allEnrolledClasses;
    }

    private static Predicate<EnrolledClass> isActiveClass() {
        return enrolledClass -> enrolledClass.getEndDate() == null
                || enrolledClass.getEndDate().isAfter(LocalDate.now());
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
        Student student = getStudentById(studentId);
        student.getEnrolledClasses().removeIf(aClass -> classId.equals(aClass.getId()));
        student.setFinancialSummary(StudentUtil.computeStudentFinancialSummary(student));

        studentRepository.save(student);
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
