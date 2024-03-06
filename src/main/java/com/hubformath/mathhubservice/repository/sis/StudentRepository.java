package com.hubformath.mathhubservice.repository.sis;

import com.hubformath.mathhubservice.model.sis.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
