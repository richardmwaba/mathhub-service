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

import com.hubformath.mathhubservice.models.ops.cashbook.Asset;
import com.hubformath.mathhubservice.models.ops.cashbook.Equity;
import com.hubformath.mathhubservice.models.ops.cashbook.Expense;
import com.hubformath.mathhubservice.models.ops.cashbook.Income;
import com.hubformath.mathhubservice.models.ops.cashbook.Liability;
import com.hubformath.mathhubservice.models.ops.cashbook.Transaction;

@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String typeName;
    
    private String typeDescription;

    @OneToOne(mappedBy = "paymentMethod")
    private Transaction transaction;

    @OneToOne(mappedBy = "paymentMethod")
    private Liability liability;

    @OneToOne(mappedBy = "paymentMethod")
    private Income income;

    @OneToOne(mappedBy = "paymentMethod")
    private Expense expense;

    @OneToOne(mappedBy = "paymentMethod")
    private Equity equity;

    @OneToOne(mappedBy = "paymentMethod")
    private Asset asset;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PaymentMethod() {}

    public PaymentMethod(String typeName, String typeDescription) {
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
    
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Liability getLiability() {
        return liability;
    }

    public void setLiability(Liability liability) {
        this.liability = liability;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Equity getEquity() {
        return equity;
    }

    public void setEquity(Equity equity) {
        this.equity = equity;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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
        if (!(o instanceof PaymentMethod))
            return false;
        PaymentMethod paymentMethod = (PaymentMethod) o;
        return Objects.equals(this.id, paymentMethod.id) && Objects.equals(this.typeName, paymentMethod.typeName)
            && Objects.equals(this.typeDescription, paymentMethod.typeDescription)
            && Objects.equals(this.transaction, paymentMethod.transaction)
            && Objects.equals(this.liability, paymentMethod.liability)
            && Objects.equals(this.income, paymentMethod.income)
            && Objects.equals(this.expense, paymentMethod.expense)
            && Objects.equals(this.asset, paymentMethod.asset)
            && Objects.equals(this.createdAt, paymentMethod.createdAt)
            && Objects.equals(this.updatedAt, paymentMethod.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.typeName, this.typeDescription, this.transaction, this.liability, this.income,
                            this.expense, this.asset, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "PaymentMethod{id=" + this.id + ", typeName=" + this.typeName + ", typeDescription=" + this.typeDescription
                + ", transaction=" + this.transaction + ", liability=" + this.liability + ", income=" + this.income + ", expense="
                + this.expense + ", equity=" + this.equity + ", asset=" + this.asset + ", createdAt=" + this.createdAt 
                + ", updatedAt=" + this.updatedAt + "}";
    }
    
}
