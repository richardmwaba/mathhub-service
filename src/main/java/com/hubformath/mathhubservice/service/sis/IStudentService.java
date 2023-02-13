package com.hubformath.mathhubservice.service.sis;

import java.util.List;

import com.hubformath.mathhubservice.dto.sis.StudentRequestDto;
import com.hubformath.mathhubservice.model.sis.Student;
public interface IStudentService {
    List<Student> getAllStudents();

    Student createStudent(StudentRequestDto student);

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
