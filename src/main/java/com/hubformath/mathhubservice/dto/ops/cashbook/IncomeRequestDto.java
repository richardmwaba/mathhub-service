package com.hubformath.mathhubservice.dto.ops.cashbook;

import java.util.UUID;

public class IncomeRequestDto {

    private UUID paymentMethodId;

    private String narration;

    private UUID incomeTypeId;

    private Double amount;

    public UUID getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(UUID paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public UUID getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(UUID incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
