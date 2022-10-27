package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.ExpenseType;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    
}
