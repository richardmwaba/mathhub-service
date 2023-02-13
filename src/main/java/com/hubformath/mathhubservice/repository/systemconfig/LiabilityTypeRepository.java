package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;

public interface LiabilityTypeRepository extends JpaRepository<LiabilityType, Long> {
    
}
