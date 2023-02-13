package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;

public interface EquityTypeRepository extends JpaRepository<EquityType, Long> {
    
}
