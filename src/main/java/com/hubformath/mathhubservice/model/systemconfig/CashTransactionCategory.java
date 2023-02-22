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
public class CashTransactionCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

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

    public Long getId() {
        return id;
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
        return Objects.equals(this.id, cashTransactionCategory.id) && Objects.equals(this.categoryName, cashTransactionCategory.categoryName)
            && Objects.equals(this.categoryDescription, cashTransactionCategory.categoryDescription)
            && Objects.equals(this.createdAt, cashTransactionCategory.createdAt)
            && Objects.equals(this.updatedAt, cashTransactionCategory.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.categoryName, this.categoryDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "CashTransactionCategory {id=" + id + ", categoryName=" + categoryName + ", categoryDescription="
                + categoryDescription + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "}";
    }
    
}