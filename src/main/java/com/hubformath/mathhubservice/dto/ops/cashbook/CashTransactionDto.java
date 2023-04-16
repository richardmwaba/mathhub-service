package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public class CashTransactionDto {
    private UUID cashTransactionId;

    private String transactionNumber;

    private PaymentMethodDto paymentMethod;

    private CashTransactionType transactionType;

    private String narration;

    private Double amount;

    private LocalDateTime transactionDateTime;

    public UUID getCashTransactionId() {
        return cashTransactionId;
    }

    public void setCashTransactionId(UUID cashTransactionId) {
        this.cashTransactionId = cashTransactionId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CashTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(CashTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
}
