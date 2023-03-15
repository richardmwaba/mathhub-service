package com.hubformath.mathhubservice.model.sis;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class StudentFinancialSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean isStudentOwing;

    private Double amountOwing;

    public StudentFinancialSummary(boolean isStudentOwing, Double amountOwing) {
        this.isStudentOwing = isStudentOwing;
        this.amountOwing = amountOwing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStudentOwing() {
        return isStudentOwing;
    }

    public void setStudentOwing(boolean studentOwing) {
        isStudentOwing = studentOwing;
    }

    public Double getAmountOwing() {
        return amountOwing;
    }

    public void setAmountOwing(Double amountOwing) {
        this.amountOwing = amountOwing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentFinancialSummary that)) return false;
        return isStudentOwing() == that.isStudentOwing() && getId().equals(that.getId()) && getAmountOwing().equals(that.getAmountOwing());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isStudentOwing(), getAmountOwing());
    }

    @Override
    public String toString() {
        return "StudentFinancialSummary{" +
                "id=" + id +
                ", isStudentOwing=" + isStudentOwing +
                ", amountOwing=" + amountOwing +
                '}';
    }
}
