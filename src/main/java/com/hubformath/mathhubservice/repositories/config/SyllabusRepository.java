package com.hubformath.mathhubservice.repositories.config;

import com.hubformath.mathhubservice.models.config.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
}
