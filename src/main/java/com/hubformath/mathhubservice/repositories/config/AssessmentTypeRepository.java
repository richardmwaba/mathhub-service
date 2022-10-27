package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.AssessmentType;

public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, Long> {
    
}
