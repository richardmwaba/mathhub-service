package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "equities")
public class Equity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "equity_id")
    private UUID equityId;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "narration")
    private String narration;

    @OneToOne
    @JoinColumn(name = "equity_type_id")
    private EquityType equityType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "approved_by")
    private Long approvedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Equity() {
    }

    public Equity(String narration, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }


    public UUID getEquityId() {
        return equityId;
    }

    public void setEquityId(UUID equityId) {
        this.equityId = equityId;
    }

    public EquityType getEquityType() {
        return equityType;
    }

    public void setEquityType(EquityType equityType) {
        this.equityType = equityType;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
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
                && Objects.equals(getCreatedBy(), equity.getCreatedBy())
                && Objects.equals(getApprovedBy(), equity.getApprovedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEquityId(),
                            getPaymentMethod(),
                            getNarration(),
                            getEquityType(),
                            getAmount(),
                            getCreatedBy(),
                            getApprovedBy());
    }

    @Override
    public String toString() {
        return "Equity{" +
                "equityId=" + equityId +
                ", paymentMethod=" + paymentMethod +
                ", narration='" + narration + '\'' +
                ", equityType=" + equityType +
                ", amount=" + amount +
                ", createdBy=" + createdBy +
                ", approvedBy=" + approvedBy +
                '}';
    }
}
