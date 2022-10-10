package com.hubformath.mathhubservice.ops.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.ops.config.models.IncomeType;

public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
    
}