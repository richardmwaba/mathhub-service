package com.hubformath.mathhubservice.models.ops.cashbook;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.hubformath.mathhubservice.models.config.EquityType;
import com.hubformath.mathhubservice.models.config.PaymentMethod;

@Entity
public class Equity {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private String narration;

    @OneToOne
    @JoinColumn(name = "equity_type_id")
    private EquityType equityType;

    private Double amount;

    private Long createdBy;

    private Long approvedBy;

    public Equity (){}

    public Equity(String narration, Double amount, Long createdBy, Long approvedBy) {
        this.narration = narration;
        this.amount = amount;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    
    public Long getId() {
        return id;
    }

    public EquityType getEquityType() {
        return equityType;
    }

    public void setEquityType(EquityType equityType) {
        this.equityType = equityType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Equity))
            return false;
        Equity equity = (Equity) o;
        return Objects.equals(this.id, equity.id) && Objects.equals(this.equityType, equity.equityType)
            && Objects.equals(this.paymentMethod, equity.paymentMethod)
            && Objects.equals(this.narration, equity.narration)
            && Objects.equals(this.amount, equity.amount)
            && Objects.equals(this.createdBy, equity.createdBy)
            && Objects.equals(this.approvedBy, equity.approvedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.equityType, this.paymentMethod, this.narration,
                            this.amount, this.createdBy, this.approvedBy);
    }

    @Override
    public String toString() {
        return "Equity{id=" + this.id + ", equityType=" + this.equityType + ", paymentMethod=" + this.paymentMethod
                + ", narration=" + this.narration + ", amount=" + this.amount + ", createdBy=" 
                + this.createdBy + ", approvedBy=" + this.approvedBy + "}";
    }
}
