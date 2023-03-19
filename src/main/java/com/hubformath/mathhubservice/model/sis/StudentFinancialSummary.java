package com.hubformath.mathhubservice.model.sis;

import java.util.Objects;

public class StudentFinancialSummary {

    private boolean isStudentOwing;

    private Double amountOwing;

    public StudentFinancialSummary(boolean isStudentOwing, Double amountOwing) {
        this.isStudentOwing = isStudentOwing;
        this.amountOwing = amountOwing;
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
        return isStudentOwing() == that.isStudentOwing() && getAmountOwing().equals(that.getAmountOwing());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isStudentOwing(), getAmountOwing());
    }

    @Override
    public String toString() {
        return "StudentFinancialSummary{" +
                ", isStudentOwing=" + isStudentOwing +
                ", amountOwing=" + amountOwing +
                '}';
    }
}
