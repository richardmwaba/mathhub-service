package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.Syllabus;

import java.util.UUID;

public interface SyllabusRepository extends JpaRepository<Syllabus, UUID> {
}
