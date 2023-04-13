package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseStatus;

import java.util.UUID;

public class ExpenseRequestDto {
    private UUID paymentMethodId;

    private String narration;

    private ExpenseStatus status;

    private UUID expenseTypeId;

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

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public UUID getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(UUID expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }  
}
