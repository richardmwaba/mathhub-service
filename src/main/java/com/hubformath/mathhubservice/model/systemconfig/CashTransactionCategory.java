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
@Table(name = "cash_transaction_categories")
public class CashTransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cash_transaction_category_id", updatable = false, nullable = false)
    private UUID cashTransactionCategoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_description", nullable = false)
    private String categoryDescription;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
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
        if (this == o) return true;
        if (!(o instanceof CashTransactionCategory that)) return false;
        return Objects.equals(getCashTransactionCategoryId(), that.getCashTransactionCategoryId())
                && Objects.equals(getCategoryName(), that.getCategoryName())
                && Objects.equals(getCategoryDescription(), that.getCategoryDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCashTransactionCategoryId(), getCategoryName(), getCategoryDescription());
    }

    @Override
    public String toString() {
        return "CashTransactionCategory{" +
                "cashTransactionCategoryId=" + cashTransactionCategoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }
}