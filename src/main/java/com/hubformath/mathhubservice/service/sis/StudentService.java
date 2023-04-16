package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.dto.sis.LessonDto;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.model.sis.Lesson;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final GradeService gradeService;

    private final ExamBoardService examBoardService;

    private final SubjectService subjectService;

    private final LessonRateService lessonRateService;

    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;


    public StudentService(final StudentRepository studentRepository,
                          final GradeService gradeService,
                          final ExamBoardService examBoardService,
                          final SubjectService subjectService,
                          final LessonRateService lessonRateService,
                          final ModelMapperConfig modelMapperConfig) {
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

    public Student getStudentById(UUID studentId) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setStudentFinancialSummary(computeStudentFinancialSummary(student));
                    return student;
                })
                .orElseThrow();
    }

    public Student createStudent(StudentDto studentRequest) {
        final UUID gradeId = studentRequest.getGradeId();
        final UUID examBoardId = studentRequest.getExamBoardId();
        final String firstName = studentRequest.getFirstName();
        final String middleName = studentRequest.getMiddleName();
        final String lastName = studentRequest.getLastName();
        final StudentGender gender = studentRequest.getGender();
        final String email = studentRequest.getEmail();
        final LocalDate dateOfBirth = studentRequest.getDateOfBirth();
        final Parent parent = modelMapper.map(studentRequest.getParent(), Parent.class);
        final List<Address> addresses = studentRequest.getAddresses()
                .stream()
                .map(address -> modelMapper.map(address, Address.class))
                .toList();
        final List<PhoneNumber> phoneNumbers = studentRequest.getPhoneNumbers()
                .stream()
                .map(phoneNumber -> modelMapper.map(phoneNumber, PhoneNumber.class))
                .toList();

        final Grade grade = gradeService.getGradeById(gradeId);
        final ExamBoard examBoard = examBoardService.getExamBoardById(examBoardId);

        final Student newStudent = new Student(firstName, middleName, lastName, email, dateOfBirth, gender);
        newStudent.setGrade(grade);
        newStudent.setExamBoard(examBoard);
        newStudent.setParent(parent);
        newStudent.setPhoneNumbers(phoneNumbers);
        newStudent.setAddresses(addresses);

        return studentRepository.save(newStudent);
    }

    public Student updateStudent(UUID studentId, Student studentRequest) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    Optional.ofNullable(studentRequest.getFirstName()).ifPresent(student::setFirstName);
                    Optional.ofNullable(studentRequest.getMiddleName()).ifPresent(student::setMiddleName);
                    Optional.ofNullable(studentRequest.getLastName()).ifPresent(studentRequest::setLastName);
                    Optional.ofNullable(studentRequest.getGender()).ifPresent(student::setGender);
                    Optional.ofNullable(studentRequest.getEmail()).ifPresent(student::setEmail);
                    Optional.ofNullable(studentRequest.getGrade()).ifPresent(student::setGrade);
                    Optional.ofNullable(studentRequest.getLessons()).ifPresent(student::setLessons);
                    Optional.ofNullable(studentRequest.getParent()).ifPresent(student::setParent);
                    Optional.ofNullable(studentRequest.getAddresses()).ifPresent(student::setAddresses);
                    Optional.ofNullable(studentRequest.getPhoneNumbers()).ifPresent(student::setPhoneNumbers);
                    Optional.ofNullable(studentRequest.getExamBoard()).ifPresent(student::setExamBoard);
                    return studentRepository.save(student);
                })
                .orElseThrow();
    }

    public Student addLessonToStudent(final UUID studentId, final LessonDto lesson) {
        Student student = getStudentById(studentId);
        Subject subject = subjectService.getSubjectById(lesson.getSubjectId());
        LessonRate lessonRate = lessonRateService.getLessonRateBySubjectComplexity(subject.getSubjectComplexity());
        Lesson newlesson = modelMapper.map(lesson, Lesson.class);
        newlesson.setSubject(subject);
        newlesson.setLessonRateAmount(lessonRate.getAmountPerLesson());
        newlesson.setLessonPaymentStatus(PaymentStatus.UNPAID);
        student.getLessons().add(newlesson);
        student.setStudentFinancialSummary(computeStudentFinancialSummary(student));

        return studentRepository.save(student);
    }

    public StudentFinancialSummary computeStudentFinancialSummary(final Student student ) {
        Double amountOwing = student.getLessons()
                .stream()
                .filter(lesson -> lesson.getLessonPaymentStatus() == PaymentStatus.UNPAID)
                .map(lesson -> lesson.getLessonRateAmount() * lesson.getOccurrence())
                .reduce(Double::sum)
                .orElse(0d);
        return new StudentFinancialSummary(student.isOwingPayment(), amountOwing);
    }

    public void deleteStudent(UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow();

        studentRepository.delete(student);
    }
}
