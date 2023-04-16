package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.EquityTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

import java.util.UUID;

public class EquityDto {
    private UUID equityId;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private EquityTypeDto equityType;

    private Double amount;

    public UUID getEquityId() {
        return equityId;
    }

    public void setEquityId(UUID equityId) {
        this.equityId = equityId;
    }

    public PaymentMethodDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public EquityTypeDto getType() {
        return equityType;
    }

    public void setType(EquityTypeDto equityType) {
        this.equityType = equityType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
