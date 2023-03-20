package com.hubformath.mathhubservice.model.sis;

import java.util.Objects;

public class StudentActionGetOwing extends StudentActionBase{
    private Long studentId;

    private boolean isStudentOwing;

    private Double amountOwing;

    public StudentActionGetOwing(StudentActionTypeEnum actionType, Long studentId, boolean isStudentOwing, Double amountOwing) {
        super(actionType);
        this.studentId = studentId;
        this.isStudentOwing = isStudentOwing;
        this.amountOwing = amountOwing;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
        if (!(o instanceof StudentActionGetOwing that)) return false;
        return isStudentOwing() == that.isStudentOwing() && getStudentId().equals(that.getStudentId()) && getAmountOwing().equals(that.getAmountOwing());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.studentId, this.isStudentOwing, this.amountOwing);
    }

    @Override
    public String toString() {
        return "StudentActionGetOwing{" +
                "studentId=" + studentId +
                ", isStudentOwing=" + isStudentOwing +
                ", amountOwing=" + amountOwing +
                '}';
    }
}
