package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.Lesson;
import com.hubformath.mathhubservice.model.sis.LessonRequest;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentFinancialSummary;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import com.hubformath.mathhubservice.service.systemconfig.ExamBoardService;
import com.hubformath.mathhubservice.service.systemconfig.GradeService;
import com.hubformath.mathhubservice.service.systemconfig.LessonRateService;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final GradeService gradeService;

    private final ExamBoardService examBoardService;

    private final SubjectService subjectService;

    private final LessonRateService lessonRateService;

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository,
                          GradeService gradeService,
                          ExamBoardService examBoardService,
                          SubjectService subjectService,
                          LessonRateService lessonRateService) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.examBoardService = examBoardService;
        this.subjectService = subjectService;
        this.lessonRateService = lessonRateService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                                .map(student -> {
                                    student.setStudentFinancialSummary(computeStudentFinancialSummary(student));
                                    return student;
                                })
                                .orElseThrow();
    }

    public Student updateStudent(String studentId, StudentRequest studentRequest) {
        return studentRepository.findById(studentId)
                                .map(student -> {
                                    Optional.ofNullable(studentRequest.firstName()).ifPresent(student::setFirstName);
                                    Optional.ofNullable(studentRequest.middleName())
                                            .ifPresent(student::setMiddleName);
                                    Optional.ofNullable(studentRequest.lastName())
                                            .ifPresent(student::setLastName);
                                    Optional.ofNullable(studentRequest.gender()).ifPresent(student::setGender);
                                    Optional.ofNullable(studentRequest.email()).ifPresent(student::setEmail);
                                    Optional.ofNullable(studentRequest.gradeId()).ifPresent(gradeId -> {
                                        Grade grade = gradeService.getGradeById(gradeId);
                                        student.setGrade(grade);
                                    });
                                    Optional.ofNullable(studentRequest.parents()).ifPresent(student::setParents);
                                    Optional.ofNullable(studentRequest.addresses()).ifPresent(student::setAddresses);
                                    Optional.ofNullable(studentRequest.phoneNumbers())
                                            .ifPresent(student::setPhoneNumbers);
                                    Optional.ofNullable(studentRequest.examBoardId()).ifPresent(examBoardId -> {
                                        ExamBoard examBoard = examBoardService.getExamBoardById(examBoardId);
                                        student.setExamBoard(examBoard);
                                    });
                                    return studentRepository.save(student);
                                })
                                .orElseThrow();
    }

    public Student addLessonToStudent(final String studentId, final LessonRequest lessonRequest) {
        Student student = getStudentById(studentId);
        Subject subject = subjectService.getSubjectById(lessonRequest.subjectId());
        LessonRate lessonRate = lessonRateService.getLessonRateBySubjectComplexity(subject.getSubjectComplexity());
        Lesson newlesson = getNewlesson(lessonRequest, subject, lessonRate);
        student.getLessons().add(newlesson);
        student.setStudentFinancialSummary(computeStudentFinancialSummary(student));

        return studentRepository.save(student);
    }

    private static Lesson getNewlesson(LessonRequest lessonRequest, Subject subject, LessonRate lessonRate) {
        Lesson newlesson = new Lesson(subject,
                                      lessonRequest.occurrence(),
                                      lessonRequest.lessonStartDate(),
                                      lessonRate.getAmountPerLesson(),
                                      lessonRequest.lessonDuration(),
                                      lessonRequest.lessonPeriod(),
                                      lessonRequest.sessionType());
        newlesson.setLessonPaymentStatus(PaymentStatus.UNPAID);
        return newlesson;
    }

    public StudentFinancialSummary computeStudentFinancialSummary(final Student student) {
        Double amountOwing = student.getLessons()
                                    .stream()
                                    .filter(lesson -> lesson.getLessonPaymentStatus() == PaymentStatus.UNPAID)
                                    .map(lesson -> lesson.getLessonRateAmount() * lesson.getOccurrence())
                                    .reduce(Double::sum)
                                    .orElse(0d);
        boolean isStudentOwing = isStudentOwing(student, amountOwing);
        return new StudentFinancialSummary(isStudentOwing, amountOwing);
    }

    private boolean isStudentOwing(Student student, Double amountOwing) {
        boolean hasUnpaidLessons = student.getLessons()
                                          .stream()
                                          .anyMatch(lesson -> lesson.getLessonPaymentStatus() == PaymentStatus.UNPAID);
        return hasUnpaidLessons && amountOwing > 0;
    }

    public void deleteStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
                                           .orElseThrow();

        studentRepository.delete(student);
    }
}
