package com.hubformath.mathhubservice.model.systemconfig;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class LiabilityType {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String typeName;

    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public LiabilityType() {}

    public LiabilityType(String typeName, String typeDescription) {
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
        if (!(o instanceof LiabilityType))
            return false;
        LiabilityType liabilityType = (LiabilityType) o;
        return Objects.equals(this.id, liabilityType.id) && Objects.equals(this.typeName, liabilityType.typeName)
            && Objects.equals(this.typeDescription, liabilityType.typeDescription)
            && Objects.equals(this.createdAt, liabilityType.createdAt)
            && Objects.equals(this.updatedAt, liabilityType.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "LiabilityType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + ", typeDescription='" 
                + this.typeDescription + ", liability='" + ", createdAt='" + this.createdAt
                + ", updatedAt='" + this.updatedAt + "'}";
    }
}
