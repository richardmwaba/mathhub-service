package com.hubformath.mathhubservice.models.config;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hubformath.mathhubservice.models.ops.cashbook.Equity;;

@Entity
public class EquityType {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String typeName;

    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public EquityType() {}

    public EquityType(String typeName, String typeDescription) {
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
        if (!(o instanceof EquityType))
            return false;
        EquityType equityType = (EquityType) o;
        return Objects.equals(this.id, equityType.id) && Objects.equals(this.typeName, equityType.typeName)
            && Objects.equals(this.typeDescription, equityType.typeDescription)
            && Objects.equals(this.createdAt, equityType.createdAt)
            && Objects.equals(this.updatedAt, equityType.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "EquityType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + ", typeDescription='" 
                + this.typeDescription + ", equity='" + ", createdAt='" + this.createdAt
                + ", updatedAt='" + this.updatedAt + "'}";
    }

}
