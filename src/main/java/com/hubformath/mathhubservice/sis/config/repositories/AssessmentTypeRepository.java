package com.hubformath.mathhubservice.sis.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.sis.config.models.AssessmentType;

public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, Long> {
    
}
