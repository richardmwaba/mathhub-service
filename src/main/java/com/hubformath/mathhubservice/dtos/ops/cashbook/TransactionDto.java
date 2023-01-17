package com.hubformath.mathhubservice.dtos.ops.cashbook;

import java.time.LocalDateTime;
import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;
import com.hubformath.mathhubservice.models.ops.cashbook.TransactionType;

public class TransactionDto {
    private Long id;

    private String transactionNumber;

    private PaymentMethodDto paymentMethod;

    private TransactionType transactionType;

    private String narration;

    private Double amount;

    private LocalDateTime transactionDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDate(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

}
