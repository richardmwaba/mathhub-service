package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

import java.time.LocalDate;
import java.util.Set;

public class TuitionPaymentDto {
    private Long id;

    private CashTransactionDto cashTransaction;

    private StudentDto student;

    private Long studentId;

    private Set<Long> lessonsIds;

    private Long paymentMethodId;

    private PaymentMethodDto paymentMethod;

    private LocalDate paymentDate;

    private Double amount;

    private ReceiptDto receipt;

    private String narration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CashTransactionDto getCashTransaction() {
        return cashTransaction;
    }

    public void setCashTransaction(CashTransactionDto cashTransaction) {
        this.cashTransaction = cashTransaction;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

    @JsonIgnore
    public Long getStudentId() {
        return studentId;
    }

    @JsonProperty
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @JsonIgnore
    public Set<Long> getLessonsIds() {
        return lessonsIds;
    }

    @JsonProperty
    public void setLessonsIds(Set<Long> lessonsIds) {
        this.lessonsIds = lessonsIds;
    }

    @JsonIgnore
    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    @JsonProperty
    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public PaymentMethodDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ReceiptDto getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptDto receipt) {
        this.receipt = receipt;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
