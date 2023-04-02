package com.hubformath.mathhubservice.model.systemconfig;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class IncomeType {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String typeName;

    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public IncomeType() {}

    public IncomeType(String typeName, String typeDescription) {
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
        if (!(o instanceof IncomeType))
            return false;
        IncomeType incomeType = (IncomeType) o;
        return Objects.equals(this.id, incomeType.id) && Objects.equals(this.typeName, incomeType.typeName)
            && Objects.equals(this.typeDescription, incomeType.typeDescription)
            && Objects.equals(this.createdAt, incomeType.createdAt)
            && Objects.equals(this.updatedAt, incomeType.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "IncomeType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" +  ", typeDescription='" 
                + this.typeDescription + ", income='" + ", createdAt='" + this.createdAt
                + ", updatedAt='" + this.updatedAt +"'}";
    }
}
