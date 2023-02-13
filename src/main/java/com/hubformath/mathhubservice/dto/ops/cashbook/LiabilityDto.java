package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.LiabilityTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

public class LiabilityDto {
    private Long id;

    private LiabilityTypeDto liabilityType;

    private PaymentMethodDto paymentMethod;

    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LiabilityTypeDto getType() {
        return liabilityType;
    }

    public void setType(LiabilityTypeDto liabilityType) {
        this.liabilityType = liabilityType;
    }

    public PaymentMethodDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
