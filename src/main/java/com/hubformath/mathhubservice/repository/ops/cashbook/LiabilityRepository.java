package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;

import java.util.UUID;

public interface LiabilityRepository extends JpaRepository<Liability, UUID> {
    
}
