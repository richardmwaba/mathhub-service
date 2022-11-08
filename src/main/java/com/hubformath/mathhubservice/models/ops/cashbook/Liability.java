package com.hubformath.mathhubservice.models.ops.cashbook;

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

import com.hubformath.mathhubservice.models.config.LiabilityType;
import com.hubformath.mathhubservice.models.config.PaymentMethod;

@Entity
public class Liability {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    @OneToOne
    @JoinColumn(name = "liability_type_id")
    private LiabilityType liabilityType;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private Double amount;

    private Long createdBy;

    private Long approvedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Liability(){}

    public Liability(Double amount, Long createdBy, Long approvedBy) {
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    public Long getId() {
        return id;
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
        if (this == o)
            return true;
        if (!(o instanceof Liability))
            return false;
        Liability liability = (Liability) o;
        return Objects.equals(this.id, liability.id) 
            && Objects.equals(this.liabilityType, liability.liabilityType)
            && Objects.equals(this.paymentMethod, liability.paymentMethod)
            && Objects.equals(this.amount, liability.amount)
            && Objects.equals(this.createdBy, liability.createdBy)
            && Objects.equals(this.approvedBy, liability.approvedBy)
            && Objects.equals(this.createdAt, liability.createdAt)
            && Objects.equals(this.updatedAt, liability.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Liability{id=" + this.id + ", liabilityType=" + this.liabilityType + ", paymentMethod=" 
                + this.paymentMethod + ", amount=" + this.amount + ", createdBy=" + this.createdBy 
                + ", approvedBy=" + this.approvedBy + ", createdAt=" + this.createdAt + ", updatedAt=" 
                + this.updatedAt +"}";
    }
}
