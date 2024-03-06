package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "expense_id")
    private String expenseId;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "narration")
    private String narration;

    @Column(name = "status")
    private ExpenseStatus status;

    @OneToOne
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "approved_by")
    private Long approvedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Expense() {
    }

    public Expense(String narration, ExpenseStatus status, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.status = status;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
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
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return Objects.equals(getExpenseId(), expense.getExpenseId())
                && Objects.equals(getPaymentMethod(), expense.getPaymentMethod())
                && Objects.equals(getNarration(), expense.getNarration())
                && getStatus() == expense.getStatus()
                && Objects.equals(getExpenseType(), expense.getExpenseType())
                && Objects.equals(getAmount(), expense.getAmount())
                && Objects.equals(getCreatedBy(), expense.getCreatedBy())
                && Objects.equals(getApprovedBy(), expense.getApprovedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpenseId(),
                            getPaymentMethod(),
                            getNarration(),
                            getStatus(),
                            getExpenseType(),
                            getAmount(),
                            getCreatedBy(),
                            getApprovedBy());
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", paymentMethod=" + paymentMethod +
                ", narration='" + narration + '\'' +
                ", status=" + status +
                ", expenseType=" + expenseType +
                ", amount=" + amount +
                ", createdBy=" + createdBy +
                ", approvedBy=" + approvedBy +
                '}';
    }
}
