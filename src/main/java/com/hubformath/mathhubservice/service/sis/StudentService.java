package com.hubformath.mathhubservice.service.sis;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hubformath.mathhubservice.dto.sis.StudentRequestDto;
import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import com.hubformath.mathhubservice.service.systemconfig.ExamBoardService;
import com.hubformath.mathhubservice.service.systemconfig.GradeService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final GradeService gradeService;

    private final ExamBoardService examBoardService;

    private final StudentRepository studentRepository;

    private final String notFoundItemName;
    
    public StudentService(final StudentRepository studentRepository, final GradeService gradeService, final ExamBoardService examBoardService) {
        super();
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
        this.examBoardService = examBoardService;
        this.notFoundItemName = "student";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if(student.isPresent()){
            return student.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public Student createStudent(StudentRequestDto studentRequest) {
        final long gradeId = studentRequest.getGradeId();
        final long examBoardId = studentRequest.getExamBoardId();
        final String firstName = studentRequest.getFirstName();
        final String middleName = studentRequest.getMiddleName();
        final String lastName = studentRequest.getLastName();
        final String email = studentRequest.getEmail();
        final LocalDate dateOfBirth = studentRequest.getDateOfBirth();
        final Parent parent = studentRequest.getParent();
        final List<Address> addresses = studentRequest.getAddresses();
        final List<PhoneNumber> phoneNumber = studentRequest.getPhoneNumber();

        final Grade grade = gradeService.getGradeById(gradeId);
        final ExamBoard examBoard = examBoardService.getExamBoardById(examBoardId);

        final Student newStudent = new Student(firstName, middleName, lastName, email,dateOfBirth);
        newStudent.setGrade(grade);
        newStudent.setExamBoard(examBoard);
        newStudent.setParent(parent);
        newStudent.setPhoneNumbers(phoneNumber);
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
                    student.setParent(studentRequest.getParent());
                    student.setAddresses(studentRequest.getAddresses());
                    student.setPhoneNumbers(studentRequest.getPhoneNumbers());
                    student.setDateOfBirth(studentRequest.getDateOfBirth());
                    student.setExamBoard(studentRequest.getExamBoard());
                    return studentRepository.save(student);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        studentRepository.delete(student);
    }
}
