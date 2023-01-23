package com.hubformath.mathhubservice.services.sis.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hubformath.mathhubservice.dtos.sis.StudentRequestDto;
import com.hubformath.mathhubservice.models.config.Grade;
import com.hubformath.mathhubservice.models.config.Syllabus;
import com.hubformath.mathhubservice.models.sis.Address;
import com.hubformath.mathhubservice.models.sis.Parent;
import com.hubformath.mathhubservice.models.sis.PhoneNumber;
import com.hubformath.mathhubservice.services.config.IGradeService;
import com.hubformath.mathhubservice.services.config.ISyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.sis.Student;
import com.hubformath.mathhubservice.repositories.sis.StudentRepository;
import com.hubformath.mathhubservice.services.sis.IStudentService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IGradeService gradeService;

    @Autowired
    ISyllabusService syllabusService;

    private final StudentRepository studentRepository;
    private final String notFoundItemName;
    
    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
        this.notFoundItemName = "student";
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if(student.isPresent()){
            return student.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Student createStudent(StudentRequestDto studentRequest) {
        final long gradeId = studentRequest.getGradeId();
        final long syllabusId = studentRequest.getSyllabusId();
        final String firstName = studentRequest.getFirstName();
        final String middleName = studentRequest.getMiddleName();
        final String lastName = studentRequest.getLastName();
        final String email = studentRequest.getEmail();
        final LocalDate dateOfBirth = studentRequest.getDateOfBirth();
        final Parent parent = studentRequest.getParent();
        final List<Address> addresses = studentRequest.getAddresses();
        final List<PhoneNumber> phoneNumber = studentRequest.getPhoneNumber();

        final Grade grade = gradeService.getGradeById(gradeId);
        final Syllabus syllabus = syllabusService.getSyllabusById(syllabusId);

        final Student newStudent = new Student(firstName, middleName, lastName, email,dateOfBirth);
        newStudent.setGrade(grade);
        newStudent.setSyllabus(syllabus);
        newStudent.setParent(parent);
        newStudent.setPhoneNumbers(phoneNumber);
        newStudent.setAddresses(addresses);

        return studentRepository.save(newStudent);
    }

    @Override
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
                    student.setSyllabus(studentRequest.getSyllabus());
                    return studentRepository.save(student);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        studentRepository.delete(student);
    }
}
