package com.hubformath.mathhubservice.model.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "narration")
    private String narration;

    @ManyToOne
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    public Expense() {
        // Required by JPA
    }

    public Expense(PaymentMethod paymentMethod, String narration, ExpenseType expenseType, Double amount, User createdBy) {
        this.paymentMethod = paymentMethod;
        this.narration = narration;
        this.expenseType = expenseType;
        this.amount = amount;
        this.createdBy = createdBy;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
                && Objects.equals(getExpenseType(), expense.getExpenseType())
                && Objects.equals(getAmount(), expense.getAmount())
                && Objects.equals(getCreatedBy(), expense.getCreatedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpenseId(),
                            getPaymentMethod(),
                            getNarration(),
                            getExpenseType(),
                            getAmount(),
                            getCreatedBy());
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", paymentMethod=" + paymentMethod +
                ", narration='" + narration + '\'' +
                ", expenseType=" + expenseType +
                ", amount=" + amount +
                ", createdBy=" + createdBy +
                '}';
    }
}
