package com.hubformath.mathhubservice.model.ops.cashbook;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private String narration;

    private ExpenseStatus status;
    
    @OneToOne
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    private Double amount;

    private Long createdBy;

    private Long approvedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Expense (){}

    public Expense(String narration, ExpenseStatus status, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.status = status;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }
  
    public Long getId() {
        return id;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
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
        if (!(o instanceof Expense))
            return false;
        Expense expense = (Expense) o;
        return Objects.equals(this.id, expense.id) && Objects.equals(this.expenseType, expense.expenseType)
            && Objects.equals(this.paymentMethod, expense.paymentMethod)
            && Objects.equals(this.narration, expense.narration)
            && Objects.equals(this.status, expense.status)
            && Objects.equals(this.amount, expense.amount)
            && Objects.equals(this.createdBy, expense.createdBy)
            && Objects.equals(this.approvedBy, expense.approvedBy)
            && Objects.equals(this.createdAt, expense.createdAt)
            && Objects.equals(this.updatedAt, expense.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.expenseType, this.paymentMethod, this.narration,
                            this.status, this.amount, this.createdBy, this.approvedBy, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Expense{id=" + this.id + ", expenseType=" + this.expenseType + ", paymentMethod=" + this.paymentMethod
                + ", narration=" + this.narration + ", status=" + this.status + ", amount=" + this.amount + ", createdBy=" 
                + this.createdBy + ", approvedBy=" + this.approvedBy + ", createdAt=" + this.createdAt 
                + ", updatedAt=" + this.updatedAt + "}";
    } 
}
