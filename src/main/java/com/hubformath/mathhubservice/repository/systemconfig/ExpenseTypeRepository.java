package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;

import java.util.UUID;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, UUID> {
    
}
