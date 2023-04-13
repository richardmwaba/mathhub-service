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
@Table(name = "cash_transaction_category")
public class CashTransactionCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID cashTransactionCategoryId;

    private String categoryName;

    private String categoryDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CashTransactionCategory() {
    }

    public CashTransactionCategory(String categoryName, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public UUID getCashTransactionCategoryId() {
        return cashTransactionCategoryId;
    }

    public void setCashTransactionCategoryId(UUID cashTransactionCategoryId) {
        this.cashTransactionCategoryId = cashTransactionCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
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
        if (!(o instanceof CashTransactionCategory cashTransactionCategory))
            return false;
        return Objects.equals(this.cashTransactionCategoryId, cashTransactionCategory.cashTransactionCategoryId) && Objects.equals(this.categoryName, cashTransactionCategory.categoryName)
            && Objects.equals(this.categoryDescription, cashTransactionCategory.categoryDescription)
            && Objects.equals(this.createdAt, cashTransactionCategory.createdAt)
            && Objects.equals(this.updatedAt, cashTransactionCategory.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cashTransactionCategoryId, this.categoryName, this.categoryDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "CashTransactionCategory {id=" + cashTransactionCategoryId + ", categoryName=" + categoryName + ", categoryDescription="
                + categoryDescription + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "}";
    }
    
}