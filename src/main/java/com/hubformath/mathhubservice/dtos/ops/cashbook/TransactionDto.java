package com.hubformath.mathhubservice.dtos.ops.cashbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;
import com.hubformath.mathhubservice.models.ops.cashbook.TransactionType;

public class TransactionDto {
    private Long id;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String transactionNumber;

    private PaymentMethodDto paymentMethod;

    private TransactionType type;

    private String narration;

    private Double amount;

    private String transactionDate;

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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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

    public Date getTransactionDateConverted(String timezone) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(this.transactionDate);
    }

    public void setTransactionDate(Date transactionDate, String timezone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.transactionDate = dateFormat.format(transactionDate);
    }
}
