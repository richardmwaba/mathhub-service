package com.hubformath.mathhubservice.model.systemconfig;

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
@Table(name = "expense_type")
public class ExpenseType {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID expenseTypeId;

    private String typeName;

    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public ExpenseType() {}

    public ExpenseType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public UUID getExpenseTypeId() {
        return this.expenseTypeId;
    }

    public void setExpenseTypeId(UUID expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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
        if (!(o instanceof ExpenseType that)) return false;
        return Objects.equals(getExpenseTypeId(), that.getExpenseTypeId())
                && Objects.equals(getTypeName(), that.getTypeName())
                && Objects.equals(getTypeDescription(), that.getTypeDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpenseTypeId(), getTypeName(), getTypeDescription());
    }

    @Override
    public String toString() {
        return "ExpenseType{" +
                "expenseTypeId=" + expenseTypeId +
                ", typeName='" + typeName + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                '}';
    }
}
