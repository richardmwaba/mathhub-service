package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquityTypeRepository extends JpaRepository<EquityType, UUID> {
    
}
