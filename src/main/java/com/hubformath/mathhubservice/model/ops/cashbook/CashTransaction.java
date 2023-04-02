package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CashTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String transactionNumber;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private CashTransactionType transactionType;

    @OneToOne
    @JoinColumn(name = "transaction_category_id")
    private CashTransactionCategory transactionCategory;

    private String narration;

    private Double amount;

    private LocalDateTime transactionDateTime;

    private Long transactedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CashTransaction(final PaymentMethod paymentMethod,
                           final CashTransactionType transactionType,
                           final String narration,
                           final Double amount) {
        this.paymentMethod = paymentMethod;
        this.transactionNumber = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.transactionDateTime = LocalDateTime.now();
        //TODO: Add actual user on transacted by
        this.transactedBy = null;
    }

    @SuppressWarnings("unused")
    public CashTransaction() {
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

    public CashTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(CashTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public CashTransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(CashTransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
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

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
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
        if (this == o) return true;
        if (!(o instanceof CashTransaction that)) return false;
        return getId().equals(that.getId())
                && getTransactionNumber().equals(that.getTransactionNumber())
                && getPaymentMethod().equals(that.getPaymentMethod())
                && getTransactionType() == that.getTransactionType()
                && getTransactionCategory().equals(that.getTransactionCategory())
                && Objects.equals(getNarration(), that.getNarration())
                && getAmount().equals(that.getAmount())
                && getTransactedBy().equals(that.getTransactedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTransactionNumber(), getPaymentMethod(), getTransactionType(), getTransactionCategory(), getNarration(), getAmount(), getTransactedBy());
    }

    @Override
    public String toString() {
        return "CashTransaction{" +
                "id=" + id +
                ", transactionNumber='" + transactionNumber + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", transactionType=" + transactionType +
                ", transactionCategory=" + transactionCategory +
                ", narration='" + narration + '\'' +
                ", amount=" + amount +
                ", transactionDateTime=" + transactionDateTime +
                ", transactedBy=" + transactedBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}