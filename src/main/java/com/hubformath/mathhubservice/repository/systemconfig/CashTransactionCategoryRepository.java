package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;

import java.util.UUID;

public interface CashTransactionCategoryRepository extends JpaRepository<CashTransactionCategory, UUID> {
    
}