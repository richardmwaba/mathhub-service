package com.hubformath.mathhubservice.ops.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.ops.config.models.ExpenseType;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    
}
