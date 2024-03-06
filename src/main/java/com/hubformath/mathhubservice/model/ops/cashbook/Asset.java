package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
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
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "asset_id")
    private String assetId;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "narration")
    private String narration;

    @OneToOne
    @JoinColumn(name = "asset_type_id")
    private AssetType assetType;

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

    public Asset() {
    }

    public Asset(String narration, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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
        if (this == o) return true;
        if (!(o instanceof Asset asset)) return false;
        return Objects.equals(getAssetId(), asset.getAssetId())
                && Objects.equals(getPaymentMethod(), asset.getPaymentMethod())
                && Objects.equals(getNarration(), asset.getNarration())
                && Objects.equals(getAssetType(), asset.getAssetType())
                && Objects.equals(getAmount(), asset.getAmount())
                && Objects.equals(getCreatedBy(), asset.getCreatedBy())
                && Objects.equals(getApprovedBy(), asset.getApprovedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssetId(),
                            getPaymentMethod(),
                            getNarration(),
                            getAssetType(),
                            getAmount(),
                            getCreatedBy(),
                            getApprovedBy());
    }

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
                ", paymentMethod=" + paymentMethod +
                ", narration='" + narration + '\'' +
                ", assetType=" + assetType +
                ", amount=" + amount +
                ", createdBy=" + createdBy +
                ", approvedBy=" + approvedBy +
                '}';
    }
}
