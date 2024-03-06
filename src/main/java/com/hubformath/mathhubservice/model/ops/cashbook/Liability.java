package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
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

@Entity
@Table(name = "liabilities")
public class Liability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "liability_id")
    private String liabilityId;

    @OneToOne
    @JoinColumn(name = "liability_type_id")
    private LiabilityType liabilityType;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

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
    @Column
    private LocalDateTime updatedAt;

    public Liability() {
    }

    public Liability(Double amount, Long createdBy, Long approvedBy) {
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    public String getLiabilityId() {
        return liabilityId;
    }

    public void setLiabilityId(String liabilityId) {
        this.liabilityId = liabilityId;
    }

    public LiabilityType getLiabilityType() {
        return liabilityType;
    }

    public void setLiabilityType(LiabilityType liabilityType) {
        this.liabilityType = liabilityType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        if (!(o instanceof Liability liability)) return false;
        return Objects.equals(getLiabilityId(), liability.getLiabilityId())
                && Objects.equals(getLiabilityType(), liability.getLiabilityType())
                && Objects.equals(getPaymentMethod(), liability.getPaymentMethod())
                && Objects.equals(getAmount(), liability.getAmount())
                && Objects.equals(getCreatedBy(), liability.getCreatedBy())
                && Objects.equals(getApprovedBy(), liability.getApprovedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLiabilityId(),
                            getLiabilityType(),
                            getPaymentMethod(),
                            getAmount(),
                            getCreatedBy(),
                            getApprovedBy());
    }

    @Override
    public String toString() {
        return "Liability{" +
                "liabilityId=" + liabilityId +
                ", liabilityType=" + liabilityType +
                ", paymentMethod=" + paymentMethod +
                ", amount=" + amount +
                ", createdBy=" + createdBy +
                ", approvedBy=" + approvedBy +
                '}';
    }
}
