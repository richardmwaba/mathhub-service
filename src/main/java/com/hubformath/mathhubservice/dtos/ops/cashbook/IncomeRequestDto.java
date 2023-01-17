package com.hubformath.mathhubservice.dtos.ops.cashbook;

public class IncomeRequestDto {

    private Long paymentMethodId;

    private String narration;

    private Long incomeTypeId;

    private Double amount;

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Long getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(Long incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
}
