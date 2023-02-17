package com.hubformath.mathhubservice.repository.ops.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;

public interface TuitionPaymentRepository extends JpaRepository<TuitionPayment, Long> {

}