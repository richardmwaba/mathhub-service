package com.hubformath.mathhubservice.models.ops.cashbook;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Liability {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    public Liability() {
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
        if (!(o instanceof Liability))
            return false;
        Liability liability = (Liability) o;
        return Objects.equals(this.id, liability.id); 
            // && Objects.equals(this.liabilityType, liability.liabilityType)
            // && Objects.equals(this.paymentMethod, liability.paymentMethod)
            // && Objects.equals(this.narration, liability.narration)
            // && Objects.equals(this.status, liability.status)
            // && Objects.equals(this.amount, liability.amount)
            // && Objects.equals(this.createdBy, liability.createdBy)
            // && Objects.equals(this.approvedBy, liability.approvedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // @Override
    // public String toString() {
    //     return "Liability [id=" + this.id + ", liabilityType=" + this.liabilityType + ", paymentMethod=" + this.paymentMethod
    //             + ", narration=" + this.narration + ", status=" + this.status + ", amount=" + this.amount + ", createdBy=" 
    //             + this.createdBy + ", approvedBy=" + this.approvedBy + "]";
    // }
}
