package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.config.PasswordGeneratorConfig;
import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.EnrolledClass;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentFinancialSummary;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import com.hubformath.mathhubservice.service.systemconfig.ExamBoardService;
import com.hubformath.mathhubservice.service.systemconfig.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static com.hubformath.mathhubservice.service.systemconfig.UsersService.ROLE_IS_NOT_FOUND;

@Service
public class StudentService {

    private final GradeService gradeService;

    private final ExamBoardService examBoardService;

    private final StudentRepository studentRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          GradeService gradeService,
                          ExamBoardService examBoardService,
                          UserRoleRepository userRoleRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.examBoardService = examBoardService;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Student createStudent(StudentRequest studentRequest) throws IllegalArgumentException, NoSuchElementException {
        Grade grade = gradeService.getGradeById(studentRequest.gradeId());
        ExamBoard examBoard = examBoardService.getExamBoardById(studentRequest.examBoardId());
        String username = createUsername(studentRequest, studentRepository);
        String password = passwordEncoder.encode(PasswordGeneratorConfig.generatePassword());
        UserRole userRole = userRoleRepository.findByName(Role.ROLE_STUDENT)
                                              .orElseThrow(() -> new NoSuchElementException(ROLE_IS_NOT_FOUND));
        User user = new User(username,
                             studentRequest.firstName(),
                             studentRequest.lastName(),
                             studentRequest.gender(),
                             studentRequest.email(),
                             studentRequest.phoneNumber(),
                             password,
                             Set.of(userRole));
        user.setMiddleName(studentRequest.middleName());

        Student student = new Student(studentRequest.firstName(),
                                      studentRequest.middleName(),
                                      studentRequest.lastName(),
                                      studentRequest.email(),
                                      studentRequest.gender(),
                                      user);
        student.setParents(studentRequest.parents());
        student.setGrade(grade);
        student.setAddress(studentRequest.address());
        student.setExamBoard(examBoard);
        student.setPhoneNumber(studentRequest.phoneNumber());
        student.setDateOfBirth(studentRequest.dateOfBirth());

        return studentRepository.save(student);

    }

    private String createUsername(StudentRequest studentRequest,
                                  StudentRepository studentRepository) throws IllegalArgumentException {
        if (studentRepository.existsByEmail(studentRequest.email())) {
            throw new IllegalArgumentException("Email is already in use!");
        }
        return studentRequest.email();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll()
                                .stream()
                                .peek(student -> student.setFinancialSummary(computeStudentFinancialSummary(student)))
                                .toList();
    }

    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                                .map(student -> {
                                    student.setFinancialSummary(computeStudentFinancialSummary(student));
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
                                    Optional.ofNullable(studentRequest.dateOfBirth())
                                            .ifPresent(student::setDateOfBirth);
                                    Optional.ofNullable(studentRequest.gradeId()).ifPresent(gradeId -> {
                                        Grade grade = gradeService.getGradeById(gradeId);
                                        student.setGrade(grade);
                                    });
                                    Optional.ofNullable(studentRequest.parents()).ifPresent(student::setParents);
                                    Optional.ofNullable(studentRequest.address()).ifPresent(student::setAddress);
                                    Optional.ofNullable(studentRequest.phoneNumber())
                                            .ifPresent(phoneNumber -> setNewPhoneNumber(student, phoneNumber));
                                    Optional.ofNullable(studentRequest.examBoardId()).ifPresent(examBoardId -> {
                                        ExamBoard examBoard = examBoardService.getExamBoardById(examBoardId);
                                        student.setExamBoard(examBoard);
                                    });
                                    return studentRepository.save(student);
                                })
                                .map(student -> {
                                    student.setFinancialSummary(computeStudentFinancialSummary(student));
                                    return student;
                                })
                                .orElseThrow();
    }

    private void setNewPhoneNumber(Student student, PhoneNumber phoneNumber) {
        student.getUser().setPhoneNumber(phoneNumber);
    }

    public StudentFinancialSummary computeStudentFinancialSummary(Student student) {
        Double amountOwing = student.getEnrolledClasses()
                                    .stream()
                                    .filter(aClass -> aClass.getPaymentStatus() == PaymentStatus.UNPAID)
                                    .map(aClass -> aClass.getCostPerSession() * aClass.getOccurrencePerWeek())
                                    .reduce(Double::sum)
                                    .orElse(0d);
        boolean isStudentOwing = isStudentOwing(student, amountOwing);
        return new StudentFinancialSummary(isStudentOwing, amountOwing, computeDueDate(student));
    }

    private boolean isStudentOwing(Student student, Double amountOwing) {
        boolean hasUnpaidLessons = student.getEnrolledClasses()
                                          .stream()
                                          .anyMatch(aClass -> aClass.getPaymentStatus() == PaymentStatus.UNPAID);
        return hasUnpaidLessons && amountOwing > 0;
    }

    private LocalDate computeDueDate(Student student) {
        return student.getEnrolledClasses()
                      .stream()
                      .filter(aClass -> aClass.getPaymentStatus() == PaymentStatus.UNPAID)
                      .map(EnrolledClass::getStartDate)
                      .min(LocalDate::compareTo)
                      .map(date -> date.plusDays(30))
                      .orElse(null);
    }

    public void deleteStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
                                           .orElseThrow();

        studentRepository.delete(student);
    }
}
