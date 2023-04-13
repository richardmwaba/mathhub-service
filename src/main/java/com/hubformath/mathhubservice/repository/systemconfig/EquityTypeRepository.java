package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;

import java.util.UUID;

public interface EquityTypeRepository extends JpaRepository<EquityType, UUID> {
    
}
