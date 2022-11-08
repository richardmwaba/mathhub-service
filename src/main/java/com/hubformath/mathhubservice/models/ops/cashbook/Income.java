package com.hubformath.mathhubservice.models.ops.cashbook;

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

import com.hubformath.mathhubservice.models.config.IncomeType;
import com.hubformath.mathhubservice.models.config.PaymentMethod;

@Entity
public class Income {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private String narration;

    @OneToOne
    @JoinColumn(name = "income_type_id")
    private IncomeType incomeType;

    private Double amount;

    private Long createdBy;

    private Long approvedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Income (){}

    public Income(String narration, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    
    public Long getId() {
        return id;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
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
        if (!(o instanceof Income))
            return false;
        Income income = (Income) o;
        return Objects.equals(this.id, income.id) && Objects.equals(this.incomeType, income.incomeType)
            && Objects.equals(this.paymentMethod, income.paymentMethod)
            && Objects.equals(this.narration, income.narration)
            && Objects.equals(this.amount, income.amount)
            && Objects.equals(this.createdBy, income.createdBy)
            && Objects.equals(this.approvedBy, income.approvedBy)
            && Objects.equals(this.createdAt, income.createdAt)
            && Objects.equals(this.updatedAt, income.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.incomeType, this.paymentMethod, this.narration,
                            this.amount, this.createdBy, this.approvedBy, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Income{id=" + this.id + ", incomeType=" + this.incomeType + ", paymentMethod=" + this.paymentMethod
                + ", narration=" + this.narration + ", amount=" + this.amount + ", createdBy=" 
                + this.createdBy + ", approvedBy=" + this.approvedBy + ", createdAt=" + this.createdAt 
                + ", updatedAt=" + this.updatedAt + "}";
    }
}
