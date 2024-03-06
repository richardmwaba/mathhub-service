package com.hubformath.mathhubservice.dto.ops.cashbook;

public class IncomeRequestDto {

    private String paymentMethodId;

    private String narration;

    private String incomeTypeId;

    private Double amount;

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(String incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
