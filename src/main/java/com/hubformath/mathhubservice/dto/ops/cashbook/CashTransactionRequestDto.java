package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashTransactionRequestDto {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long paymentMethodId;

    private CashTransactionType transactionType;

    private String narration;

    private Double amount;

    private LocalDateTime transactionDateTime;

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public CashTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionTypeId(CashTransactionType transactionType) {
        this.transactionType = transactionType;
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

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDate) {
        this.transactionDateTime = LocalDateTime.parse(transactionDate, dateFormatter);
    }

}
