package com.hubformath.mathhubservice.repository.sis;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.sis.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    
}
