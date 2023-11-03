package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CashTransactionRepository extends JpaRepository<CashTransaction, UUID> {
    
}
