package com.hubformath.mathhubservice.model.ops.cashbook;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String transactionNumber;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String narration;

    private Double amount;

    private LocalDateTime transactionDateTime;

    private Long transactedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Transaction() {
    }

    public Transaction(String transactionNumber, TransactionType transactionType, String narration, Double amount,
            LocalDateTime transactionDateTime, Long transactedBy) {
        this.transactionNumber = transactionNumber;
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.transactionDateTime = transactionDateTime;
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

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDate(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
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
        return Objects.equals(this.id, transaction.id)
                && Objects.equals(this.transactionNumber, transaction.transactionNumber)
                && Objects.equals(this.paymentMethod, transaction.paymentMethod)
                && Objects.equals(this.transactionType, transaction.transactionType)
                && Objects.equals(this.narration, transaction.narration)
                && Objects.equals(this.amount, transaction.amount)
                && Objects.equals(this.transactionDateTime, transaction.transactionDateTime)
                && Objects.equals(this.transactedBy, transaction.transactedBy)
                && Objects.equals(this.createdAt, transaction.createdAt)
                && Objects.equals(this.updatedAt, transaction.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.transactionNumber, this.paymentMethod, this.transactionType,
                this.narration, this.amount, this.transactionDateTime, this.transactedBy, this.createdAt,
                this.updatedAt);
    }

    @Override
    public String toString() {
        return "Transaction{id=" + this.id + ", transactionNumber=" + this.transactionNumber + ", paymentMethod="
                + this.paymentMethod + ", transactionType=" + this.transactionType + ", narration=" + this.narration
                + ", amount="
                + this.amount + ", transactionDateTime=" + this.transactionDateTime + ", transactedBy=" + this.transactedBy
                + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }
}
