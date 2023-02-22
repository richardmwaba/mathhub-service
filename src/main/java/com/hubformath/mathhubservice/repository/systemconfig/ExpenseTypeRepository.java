package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    
}
