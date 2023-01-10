package com.hubformath.mathhubservice.dtos.ops.cashbook;

import com.hubformath.mathhubservice.models.ops.cashbook.ExpenseStatus;

public class ExpenseRequestDto {
    private long paymentMethodId;

    private String narration;

    private ExpenseStatus status;

    private long expenseTypeId;

    private Double amount;

    public long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
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

    public long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }  
}
