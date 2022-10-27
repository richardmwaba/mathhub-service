package com.hubformath.mathhubservice.repositories.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.ops.cashbook.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
