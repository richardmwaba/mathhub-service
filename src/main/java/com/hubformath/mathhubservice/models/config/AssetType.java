package com.hubformath.mathhubservice.models.config;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class AssetType {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    private String typeName;

    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AssetType() {}

    public AssetType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public Long getId() {
        return this.id;
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
        if (this == o)
            return true;
        if (!(o instanceof AssetType))
            return false;
        AssetType assetType = (AssetType) o;
        return Objects.equals(this.id, assetType.id) && Objects.equals(this.typeName, assetType.typeName)
            && Objects.equals(this.typeDescription, assetType.typeDescription)
            && Objects.equals(this.createdAt, assetType.createdAt)
            && Objects.equals(this.updatedAt, assetType.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "AssetType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + ", typeDescription='" 
                + this.typeDescription + ", asset='" + ", createdAt='" + this.createdAt
                + ", updatedAt='" + this.updatedAt + "'}";
    }
}
