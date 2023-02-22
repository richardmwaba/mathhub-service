package com.hubformath.mathhubservice.dto.ops.cashbook;

public class TuitionPaymentRequestDto {
    private Long studentId;

    private Long lessonsId;

    private Long paymentMethodId;

    private Long transactionCategoryId;

    private Double amount;

    private String narration;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getLessonsId() {
        return lessonsId;
    }

    public void setLessonsId(Long lessonsId) {
        this.lessonsId = lessonsId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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

    public Long getTransactionCategoryId() {
        return transactionCategoryId;
    }

    public void setTransactionCategoryId(Long transactionCategoryId) {
        this.transactionCategoryId = transactionCategoryId;
    }
}
