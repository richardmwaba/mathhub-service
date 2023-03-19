package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.dto.systemconfig.LessonDto;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.model.sis.Lesson;
import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.sis.StudentFinancialSummary;
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

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student createStudent(StudentDto studentRequest) {
        final long gradeId = studentRequest.getGradeId();
        final long examBoardId = studentRequest.getExamBoardId();
        final String firstName = studentRequest.getFirstName();
        final String middleName = studentRequest.getMiddleName();
        final String lastName = studentRequest.getLastName();
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

        final Student newStudent = new Student(firstName, middleName, lastName, email, dateOfBirth);
        newStudent.setGrade(grade);
        newStudent.setExamBoard(examBoard);
        newStudent.setParent(parent);
        newStudent.setPhoneNumbers(phoneNumbers);
        newStudent.setAddresses(addresses);

        return studentRepository.save(newStudent);
    }

    public Student updateStudent(Long id, Student studentRequest) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(studentRequest.getFirstName());
                    student.setMiddleName(studentRequest.getMiddleName());
                    student.setLastName(studentRequest.getLastName());
                    student.setEmail(studentRequest.getEmail());
                    student.setGrade(studentRequest.getGrade());
                    student.setLessons(studentRequest.getLessons());
                    student.setParent(studentRequest.getParent());
                    student.setAddresses(studentRequest.getAddresses());
                    student.setPhoneNumbers(studentRequest.getPhoneNumbers());
                    student.setExamBoard(studentRequest.getExamBoard());
                    return studentRepository.save(student);
                })
                .orElseThrow();
    }

    public Student addLessonToStudent(final Long studentId, final LessonDto lesson) {
        Student student = getStudentById(studentId);
        Subject subject = subjectService.getSubjectById(lesson.getSubjectId());
        LessonRate lessonRate = lessonRateService.getLessonRateBySubjectComplexity(subject.getSubjectComplexity());
        Lesson newlesson = modelMapper.map(lesson, Lesson.class);
        newlesson.setSubject(subject);
        newlesson.setLessonRateAmount(lessonRate.getAmountPerLesson());
        newlesson.setLessonPaymentStatus(PaymentStatus.UNPAID);
        student.getLessons().add(newlesson);
        student.setStudentFinancialSummary(establishStudentFinancialSummary(student));

        return studentRepository.save(student);
    }

    private StudentFinancialSummary establishStudentFinancialSummary(final Student student ) {
        Double amountOwing = student.getLessons()
                .stream()
                .filter(lesson -> lesson.getLessonPaymentStatus() == PaymentStatus.UNPAID)
                .map(lesson -> lesson.getLessonRateAmount() * lesson.getOccurrence())
                .reduce(Double::sum)
                .orElse(0d);
        return new StudentFinancialSummary(student.isOwingPayment(), amountOwing);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow();

        studentRepository.delete(student);
    }
}
