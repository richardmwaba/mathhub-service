package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.Equity;


public interface EquityRepository extends JpaRepository<Equity, Long> {
    
}
