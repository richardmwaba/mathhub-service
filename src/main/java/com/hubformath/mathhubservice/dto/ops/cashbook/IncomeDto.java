package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.IncomeTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

public class IncomeDto {

    private String incomeId;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private IncomeTypeDto incomeType;

    private Double amount;

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
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

    public IncomeTypeDto getType() {
        return incomeType;
    }

    public void setType(IncomeTypeDto incomeType) {
        this.incomeType = incomeType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
