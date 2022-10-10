package com.hubformath.mathhubservice.ops.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.ops.config.models.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    
}