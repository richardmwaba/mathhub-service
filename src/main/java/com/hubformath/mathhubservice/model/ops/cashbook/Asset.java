package com.hubformath.mathhubservice.model.ops.cashbook;

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

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private String narration;

    @OneToOne
    @JoinColumn(name = "asset_type_id")
    private AssetType assetType;

    private Double amount;

    private Long createdBy;

    private Long approvedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Asset (){}

    public Asset(String narration, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }
    
    public Long getId() {
        return id;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
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
        if (this == o)
            return true;
        if (!(o instanceof Asset))
            return false;
        Asset asset = (Asset) o;
        return Objects.equals(this.id, asset.id) && Objects.equals(this.assetType, asset.assetType)
            && Objects.equals(this.paymentMethod, asset.paymentMethod)
            && Objects.equals(this.narration, asset.narration)
            && Objects.equals(this.amount, asset.amount)
            && Objects.equals(this.createdBy, asset.createdBy)
            && Objects.equals(this.approvedBy, asset.approvedBy)
            && Objects.equals(this.createdAt, asset.createdAt)
            && Objects.equals(this.updatedAt, asset.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.assetType, this.paymentMethod, this.narration,
                            this.amount, this.createdBy, this.approvedBy, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Asset{id=" + this.id + ", assetType=" + this.assetType + ", paymentMethod=" + this.paymentMethod
                + ", narration=" + this.narration + ", amount=" + this.amount + ", createdBy=" 
                + this.createdBy + ", approvedBy=" + this.approvedBy + ", createdAt=" + this.createdBy 
                + ", updatedAt=" + this.updatedAt +"}";
    }
}
