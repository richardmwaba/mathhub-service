package com.hubformath.mathhubservice.models.ops.cashbook;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Expense {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    private Long expenseType;
    private Long paymentMethod;
    private String narration;
    private ExpenseStatus status;
    private Double amount;
    private Long createdBy;
    private Long approvedBy;

    public Expense (){}

    public Expense(Long expenseType, Long paymentMethod, String narration, ExpenseStatus status, Double amount,
            Long createdBy, Long approvedBy) {
        this.expenseType = expenseType;
        this.paymentMethod = paymentMethod;
        this.narration = narration;
        this.status = status;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    
    public Long getId() {
        return id;
    }

    public Long getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(Long expenseType) {
        this.expenseType = expenseType;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Expense))
            return false;
        Expense expense = (Expense) o;
        return Objects.equals(this.id, expense.id) && Objects.equals(this.expenseType, expense.expenseType)
            && Objects.equals(this.paymentMethod, expense.paymentMethod)
            && Objects.equals(this.narration, expense.narration)
            && Objects.equals(this.status, expense.status)
            && Objects.equals(this.amount, expense.amount)
            && Objects.equals(this.createdBy, expense.createdBy)
            && Objects.equals(this.approvedBy, expense.approvedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.expenseType, this.paymentMethod, this.narration,
                            this.status, this.amount, this.createdBy, this.approvedBy);
    }

    @Override
    public String toString() {
        return "Expense [id=" + this.id + ", expenseType=" + this.expenseType + ", paymentMethod=" + this.paymentMethod
                + ", narration=" + this.narration + ", status=" + this.status + ", amount=" + this.amount + ", createdBy=" 
                + this.createdBy + ", approvedBy=" + this.approvedBy + "]";
    } 
}
