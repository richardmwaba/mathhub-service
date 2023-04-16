package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.ExpenseTypeDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;
import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseStatus;

import java.util.UUID;

public class ExpenseDto {
    private UUID expenseId;

    private PaymentMethodDto paymentMethod;

    private String narration;

    private ExpenseStatus status;

    private ExpenseTypeDto type;

    private Double amount;

    public UUID getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(UUID expenseId) {
        this.expenseId = expenseId;
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
