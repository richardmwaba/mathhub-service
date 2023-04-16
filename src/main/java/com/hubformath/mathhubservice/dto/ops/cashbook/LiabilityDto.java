package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.LiabilityTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

import java.util.UUID;

public class LiabilityDto {
    private UUID liabilityId;

    private LiabilityTypeDto liabilityType;

    private PaymentMethodDto paymentMethod;

    private Double amount;

    public UUID getLiabilityId() {
        return liabilityId;
    }

    public void setLiabilityId(UUID liabilityId) {
        this.liabilityId = liabilityId;
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
