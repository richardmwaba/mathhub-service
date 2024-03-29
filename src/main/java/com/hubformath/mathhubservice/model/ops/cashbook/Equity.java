package com.hubformath.mathhubservice.model.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "equities")
public class Equity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "equity_id")
    private String equityId;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "narration")
    private String narration;

    @ManyToOne
    @JoinColumn(name = "equity_type_id")
    private EquityType equityType;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    public Equity() {
        // Required by JPA
    }

    public Equity(PaymentMethod paymentMethod, String narration, EquityType equityType, Double amount, User createdBy) {
        this.paymentMethod = paymentMethod;
        this.narration = narration;
        this.equityType = equityType;
        this.amount = amount;
        this.createdBy = createdBy;
    }

    public String getEquityId() {
        return equityId;
    }

    public void setEquityId(String equityId) {
        this.equityId = equityId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public EquityType getEquityType() {
        return equityType;
    }

    public void setEquityType(EquityType equityType) {
        this.equityType = equityType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
        if (!(o instanceof Equity equity)) return false;
        return Objects.equals(getEquityId(), equity.getEquityId())
                && Objects.equals(getPaymentMethod(), equity.getPaymentMethod())
                && Objects.equals(getNarration(), equity.getNarration())
                && Objects.equals(getEquityType(), equity.getEquityType())
                && Objects.equals(getAmount(), equity.getAmount())
                && Objects.equals(getCreatedBy(), equity.getCreatedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEquityId(),
                            getPaymentMethod(),
                            getNarration(),
                            getEquityType(),
                            getAmount(),
                            getCreatedBy());
    }

    @Override
    public String toString() {
        return "Equity{" +
                "equityId='" + equityId + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", narration='" + narration + '\'' +
                ", equityType=" + equityType +
                ", amount=" + amount +
                ", createdBy=" + createdBy +
                '}';
    }
}
