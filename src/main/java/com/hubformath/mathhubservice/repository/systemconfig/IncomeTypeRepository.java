package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.IncomeType;

public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
    
}