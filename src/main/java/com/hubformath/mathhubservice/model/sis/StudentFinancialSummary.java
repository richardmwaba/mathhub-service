package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDate;
import java.util.Objects;

public class StudentFinancialSummary {

    private boolean isOwing;

    private Double amountOwing;

    private LocalDate dueDate;

    public StudentFinancialSummary(boolean isOwing, Double amountOwing, LocalDate dueDate) {
        this.isOwing = isOwing;
        this.amountOwing = amountOwing;
        this.dueDate = dueDate;
    }

    public boolean isOwing() {
        return isOwing;
    }

    public void setOwing(boolean owing) {
        isOwing = owing;
    }

    public Double getAmountOwing() {
        return amountOwing;
    }

    public void setAmountOwing(Double amountOwing) {
        this.amountOwing = amountOwing;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentFinancialSummary that)) return false;
        return isOwing() == that.isOwing()
                && Objects.equals(getAmountOwing(), that.getAmountOwing())
                && Objects.equals(getDueDate(), that.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOwing(), getAmountOwing(), getDueDate());
    }

    @Override
    public String toString() {
        return "StudentFinancialSummary{" +
                "isStudentOwing=" + isOwing +
                ", amountOwing=" + amountOwing +
                ", dueDate=" + dueDate +
                '}';
    }
}
