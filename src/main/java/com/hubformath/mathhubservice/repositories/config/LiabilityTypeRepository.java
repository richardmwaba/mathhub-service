package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.LiabilityType;

public interface LiabilityTypeRepository extends JpaRepository<LiabilityType, Long> {
    
}
