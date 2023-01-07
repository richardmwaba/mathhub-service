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

import com.hubformath.mathhubservice.models.ops.cashbook.Expense;

@Entity
public class ExpenseType {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String typeName;

    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "expenseType")
    private Expense expense;

    public ExpenseType() {}

    public ExpenseType(String typeName, String typeDescription) {
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

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
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
        if (!(o instanceof ExpenseType))
            return false;
        ExpenseType expenseType = (ExpenseType) o;
        return Objects.equals(this.id, expenseType.id) && Objects.equals(this.typeName, expenseType.typeName)
            && Objects.equals(this.typeDescription, expenseType.typeDescription)
            && Objects.equals(this.expense, expenseType.expense)
            && Objects.equals(this.createdAt, expenseType.createdAt)
            && Objects.equals(this.updatedAt, expenseType.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.expense, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "ExpenseType{" + "id=" + this.id + ", typeName='" + this.typeName + "'" + ", typeDescription='" 
                + this.typeDescription + ", expense='" + this.expense + ", createdAt='" + this.createdAt 
                + ", updatedAt='" + this.updatedAt + "'}";
    }

}
