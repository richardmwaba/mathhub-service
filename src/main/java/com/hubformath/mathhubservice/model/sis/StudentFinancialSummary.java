package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDate;
import java.util.Objects;

public class StudentFinancialSummary {

    private boolean isOwing;

    private Double totalAmountOwing;

    private LocalDate dueDate;

    public StudentFinancialSummary(boolean isOwing, Double totalAmountOwing, LocalDate dueDate) {
        this.isOwing = isOwing;
        this.totalAmountOwing = totalAmountOwing;
        this.dueDate = dueDate;
    }

    public boolean isOwing() {
        return isOwing;
    }

    public void setOwing(boolean owing) {
        isOwing = owing;
    }

    public Double getTotalAmountOwing() {
        return totalAmountOwing;
    }

    public void setTotalAmountOwing(Double totalAmountOwing) {
        this.totalAmountOwing = totalAmountOwing;
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
                && Objects.equals(getTotalAmountOwing(), that.getTotalAmountOwing())
                && Objects.equals(getDueDate(), that.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOwing(), getTotalAmountOwing(), getDueDate());
    }

    @Override
    public String toString() {
        return "StudentFinancialSummary{" +
                "isStudentOwing=" + isOwing +
                ", amountOwing=" + totalAmountOwing +
                ", dueDate=" + dueDate +
                '}';
    }
}
