package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquityRepository extends JpaRepository<Equity, UUID> {
    
}
