package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.Grade;

import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID> {
}
