package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.EquityType;

public interface EquityTypeRepository extends JpaRepository<EquityType, Long> {
    
}
