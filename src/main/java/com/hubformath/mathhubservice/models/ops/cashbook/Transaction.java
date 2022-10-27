package com.hubformath.mathhubservice.models.ops.cashbook;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    private String transactionNumber;
    private Long paymentMethod;
    private TransactionType transactionType;
    private String narration;
    private Double amount;
    private Instant transactionDate;
    private Long transactedBy;

    public Transaction() {}

    public Transaction(String transactionNumber, Long paymentMethod, TransactionType transactionType,
        String narration, Double amount, Instant transactionDate, Long transactedBy) {
        this.transactionNumber = transactionNumber;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactedBy = transactedBy;
    }

    public Long getId() {
        return this.id;
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

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
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

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactedBy() {
        return transactedBy;
    }

    public void setTransactedBy(Long transactedBy) {
        this.transactedBy = transactedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Transaction))
            return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(this.id, transaction.id) && Objects.equals(this.transactionNumber, transaction.transactionNumber)
            && Objects.equals(this.paymentMethod, transaction.paymentMethod)
            && Objects.equals(this.transactionType, transaction.transactionType)
            && Objects.equals(this.narration, transaction.narration)
            && Objects.equals(this.amount, transaction.amount)
            && Objects.equals(this.transactionDate, transaction.transactionDate)
            && Objects.equals(this.transactedBy, transaction.transactedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.transactionNumber, this.paymentMethod, this.transactionType,
                            this.narration, this.amount, this.transactionDate, this.transactedBy);
    }           

    @Override
    public String toString() {
        return "Transaction {id=" + this.id + ", transactionNumber=" + this.transactionNumber + ", paymentMethod=" 
                + this.paymentMethod + ", transactionType=" + this.transactionType + ", narration=" + this.narration + ", amount=" 
                + this.amount + ", transactionDate=" + this.transactionDate + ", transactedBy=" + this.transactedBy + "}";
    }
}
