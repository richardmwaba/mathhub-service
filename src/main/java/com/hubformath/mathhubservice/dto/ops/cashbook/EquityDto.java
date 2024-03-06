package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.EquityTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

public class EquityDto {

    private String equityId;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private EquityTypeDto equityType;

    private Double amount;

    public String getEquityId() {
        return equityId;
    }

    public void setEquityId(String equityId) {
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
