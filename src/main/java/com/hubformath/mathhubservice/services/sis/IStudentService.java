package com.hubformath.mathhubservice.services.sis;

import java.util.List;

import com.hubformath.mathhubservice.models.sis.Student;
public interface IStudentService {
    List<Student> getAllStudents();

    Student createStudent(Student student);

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
