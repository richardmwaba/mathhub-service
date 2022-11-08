package com.hubformath.mathhubservice.models.ops.cashbook;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hubformath.mathhubservice.models.config.PaymentMethod;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String transactionNumber;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private TransactionType transactionType;

    private String narration;

    private Double amount;

    private Instant transactionDate;

    private Long transactedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Transaction() {}

    public Transaction(String transactionNumber, TransactionType transactionType, String narration, Double amount, 
            Instant transactionDate, Long transactedBy) {
        this.transactionNumber = transactionNumber;
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactedBy = transactedBy;
    }

    public Long getId() {
        return this.id;
    }
    
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
            && Objects.equals(this.transactedBy, transaction.transactedBy)
            && Objects.equals(this.createdAt, transaction.createdAt)
            && Objects.equals(this.updatedAt, transaction.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.transactionNumber, this.paymentMethod, this.transactionType,
                            this.narration, this.amount, this.transactionDate, this.transactedBy, this.createdAt, this.updatedAt);
    }           

    @Override
    public String toString() {
        return "Transaction{id=" + this.id + ", transactionNumber=" + this.transactionNumber + ", paymentMethod=" 
                + this.paymentMethod + ", transactionType=" + this.transactionType + ", narration=" + this.narration + ", amount=" 
                + this.amount + ", transactionDate=" + this.transactionDate + ", transactedBy=" + this.transactedBy 
                + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }
}
