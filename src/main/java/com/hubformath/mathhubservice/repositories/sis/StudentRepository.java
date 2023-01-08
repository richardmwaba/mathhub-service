package com.hubformath.mathhubservice.repositories.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.sis.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}
