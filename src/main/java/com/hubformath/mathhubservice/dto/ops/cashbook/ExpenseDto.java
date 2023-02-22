package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.ExpenseTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;
import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseStatus;

public class ExpenseDto {
    private Long id;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private ExpenseStatus status;

    private ExpenseTypeDto type;

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

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public ExpenseTypeDto getType() {
        return type;
    }

    public void setType(ExpenseTypeDto type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
