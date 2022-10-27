package com.hubformath.mathhubservice.models.ops.cashbook;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equity {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    public Equity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Equity))
            return false;
        Equity equity = (Equity) o;
        return Objects.equals(this.id, equity.id); 
            // && Objects.equals(this.equityType, equity.equityType)
            // && Objects.equals(this.paymentMethod, equity.paymentMethod)
            // && Objects.equals(this.narration, equity.narration)
            // && Objects.equals(this.status, equity.status)
            // && Objects.equals(this.amount, equity.amount)
            // && Objects.equals(this.createdBy, equity.createdBy)
            // && Objects.equals(this.approvedBy, equity.approvedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // @Override
    // public String toString() {
    //     return "Equity [id=" + this.id + ", equityType=" + this.equityType + ", paymentMethod=" + this.paymentMethod
    //             + ", narration=" + this.narration + ", status=" + this.status + ", amount=" + this.amount + ", createdBy=" 
    //             + this.createdBy + ", approvedBy=" + this.approvedBy + "]";
    // }
}
