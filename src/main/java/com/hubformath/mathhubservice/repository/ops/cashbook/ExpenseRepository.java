package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.Expense;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    
}
