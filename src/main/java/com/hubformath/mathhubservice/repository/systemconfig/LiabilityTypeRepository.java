package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LiabilityTypeRepository extends JpaRepository<LiabilityType, UUID> {
    
}
