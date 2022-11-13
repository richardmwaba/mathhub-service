package com.hubformath.mathhubservice.dtos.ops.cashbook;

import com.hubformath.mathhubservice.dtos.config.IncomeTypeDto;
import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;

public class IncomeDto {
    private Long id;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private IncomeTypeDto type;

    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return type;
    }

    public void setType(IncomeTypeDto type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
