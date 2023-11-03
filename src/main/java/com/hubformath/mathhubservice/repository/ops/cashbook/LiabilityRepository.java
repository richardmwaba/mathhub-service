package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LiabilityRepository extends JpaRepository<Liability, UUID> {
    
}
