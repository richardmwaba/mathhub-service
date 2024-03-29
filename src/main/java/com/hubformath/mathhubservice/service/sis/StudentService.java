package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.model.sis.Lesson;
import com.hubformath.mathhubservice.model.sis.LessonRequest;
import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentFinancialSummary;
import com.hubformath.mathhubservice.model.sis.StudentGender;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import com.hubformath.mathhubservice.service.systemconfig.ExamBoardService;
import com.hubformath.mathhubservice.service.systemconfig.GradeService;
import com.hubformath.mathhubservice.service.systemconfig.LessonRateService;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final GradeService gradeService;

    private final ExamBoardService examBoardService;

    private final SubjectService subjectService;

    private final LessonRateService lessonRateService;

    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;


    public StudentService(StudentRepository studentRepository,
                          GradeService gradeService,
                          ExamBoardService examBoardService,
                          SubjectService subjectService,
                          LessonRateService lessonRateService,
                          ModelMapperConfig modelMapperConfig) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.examBoardService = examBoardService;
        this.subjectService = subjectService;
        this.lessonRateService = lessonRateService;
        this.modelMapper = modelMapperConfig.createModelMapper();
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

    public Student createStudent(StudentRequest studentRequest) {
        final String gradeId = studentRequest.gradeId();
        final String examBoardId = studentRequest.examBoardId();
        final String firstName = studentRequest.firstName();
        final String middleName = studentRequest.middleName();
        final String lastName = studentRequest.lastName();
        final StudentGender gender = studentRequest.gender();
        final String email = studentRequest.email();
        final LocalDate dateOfBirth = studentRequest.dateOfBirth();
        final List<Parent> parents = studentRequest.parents();
        final List<Address> addresses = studentRequest.addresses();
        final List<PhoneNumber> phoneNumbers = studentRequest.phoneNumbers();

        final Grade grade = gradeService.getGradeById(gradeId);
        final ExamBoard examBoard = examBoardService.getExamBoardById(examBoardId);

        final Student newStudent = new Student(firstName, middleName, lastName, email, dateOfBirth, gender, parents);
        newStudent.setGrade(grade);
        newStudent.setExamBoard(examBoard);
        newStudent.setPhoneNumbers(phoneNumbers);
        newStudent.setAddresses(addresses);
        newStudent.setLessons(Collections.emptyList());

        return studentRepository.save(newStudent);
    }

    public Student updateStudent(String studentId, Student studentRequest) {
        return studentRepository.findById(studentId)
                                .map(student -> {
                                    Optional.ofNullable(studentRequest.getFirstName()).ifPresent(student::setFirstName);
                                    Optional.ofNullable(studentRequest.getMiddleName())
                                            .ifPresent(student::setMiddleName);
                                    Optional.ofNullable(studentRequest.getLastName())
                                            .ifPresent(studentRequest::setLastName);
                                    Optional.ofNullable(studentRequest.getGender()).ifPresent(student::setGender);
                                    Optional.ofNullable(studentRequest.getEmail()).ifPresent(student::setEmail);
                                    Optional.ofNullable(studentRequest.getGrade()).ifPresent(student::setGrade);
                                    Optional.ofNullable(studentRequest.getParents()).ifPresent(student::setParents);
                                    Optional.ofNullable(studentRequest.getAddresses()).ifPresent(student::setAddresses);
                                    Optional.ofNullable(studentRequest.getPhoneNumbers()).ifPresent(student::setPhoneNumbers);
                                    Optional.ofNullable(studentRequest.getExamBoard()).ifPresent(student::setExamBoard);
                                    return studentRepository.save(student);
                                })
                                .orElseThrow();
    }

    public Student addLessonToStudent(final String studentId, final LessonRequest lessonRequest) {
        Student student = getStudentById(studentId);
        Subject subject = subjectService.getSubjectById(lessonRequest.subjectId());
        LessonRate lessonRate = lessonRateService.getLessonRateBySubjectComplexity(subject.getSubjectComplexity());
        Lesson newlesson = modelMapper.map(lessonRequest, Lesson.class);
        newlesson.setSubject(subject);
        newlesson.setLessonRateAmount(lessonRate.getAmountPerLesson());
        newlesson.setLessonPaymentStatus(PaymentStatus.UNPAID);
        student.getLessons().add(newlesson);
        student.setStudentFinancialSummary(computeStudentFinancialSummary(student));

        return studentRepository.save(student);
    }

    public StudentFinancialSummary computeStudentFinancialSummary(final Student student) {
        Double amountOwing = student.getLessons()
                                    .stream()
                                    .filter(lesson -> lesson.getLessonPaymentStatus() == PaymentStatus.UNPAID)
                                    .map(lesson -> lesson.getLessonRateAmount() * lesson.getOccurrence())
                                    .reduce(Double::sum)
                                    .orElse(0d);
        return new StudentFinancialSummary(student.isOwingPayment(), amountOwing);
    }

    public void deleteStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
                                           .orElseThrow();

        studentRepository.delete(student);
    }
}
