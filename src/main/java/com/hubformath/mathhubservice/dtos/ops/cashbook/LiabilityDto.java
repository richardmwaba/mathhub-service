package com.hubformath.mathhubservice.dtos.ops.cashbook;

import com.hubformath.mathhubservice.dtos.config.LiabilityTypeDto;
import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;

public class LiabilityDto {
    private Long id;

    private LiabilityTypeDto type;

    private PaymentMethodDto paymentMethod;

    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LiabilityTypeDto getType() {
        return type;
    }

    public void setType(LiabilityTypeDto type) {
        this.type = type;
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
