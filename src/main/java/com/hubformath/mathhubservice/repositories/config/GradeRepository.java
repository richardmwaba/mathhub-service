package com.hubformath.mathhubservice.repositories.config;

import com.hubformath.mathhubservice.models.config.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
