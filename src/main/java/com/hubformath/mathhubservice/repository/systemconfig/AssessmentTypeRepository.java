package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;

public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, Long> {
    
}
