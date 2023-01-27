package com.hubformath.mathhubservice.services.sis;

import java.util.List;

import com.hubformath.mathhubservice.dtos.sis.StudentRequestDto;
import com.hubformath.mathhubservice.models.sis.Student;
public interface IStudentService {
    List<Student> getAllStudents();

    Student createStudent(StudentRequestDto student);

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
