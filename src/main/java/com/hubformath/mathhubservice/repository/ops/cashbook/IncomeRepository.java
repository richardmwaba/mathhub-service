package com.hubformath.mathhubservice.repository.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
}
