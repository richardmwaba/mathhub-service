package com.hubformath.mathhubservice.model.systemconfig;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "asset_types")
public class AssetType {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "asset_type_id", updatable = false, nullable = false)
    private UUID assetTypeId;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "type_description", nullable = false)
    private String typeDescription;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public AssetType() {}

    public AssetType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public UUID getAssetTypeId() {
        return this.assetTypeId;
    }

    public void setAssetTypeId(UUID assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return this.typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
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
        if (!(o instanceof AssetType assetType)) return false;
        return Objects.equals(getAssetTypeId(), assetType.getAssetTypeId())
                && Objects.equals(getTypeName(), assetType.getTypeName())
                && Objects.equals(getTypeDescription(), assetType.getTypeDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssetTypeId(), getTypeName(), getTypeDescription());
    }

    @Override
    public String toString() {
        return "AssetType{" +
                "assetTypeId=" + assetTypeId +
                ", typeName='" + typeName + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                '}';
    }
}
