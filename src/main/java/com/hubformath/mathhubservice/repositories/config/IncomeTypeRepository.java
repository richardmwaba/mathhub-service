package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.IncomeType;

public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
    
}