package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

import java.time.LocalDate;

public class TuitionPaymentDto {
    private Long id;

    private CashTransaction cashTransaction;

    private Student student;

    private Long studentId;

    private Long paymentMethodId;

    private Long transactionCategoryId;

    private PaymentMethod paymentMethod;

    private LocalDate paymentDate;

    private Double amount;

    private Receipt receipt;

    private String narration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CashTransaction getCashTransaction() {
        return cashTransaction;
    }

    public void setCashTransaction(CashTransaction cashTransaction) {
        this.cashTransaction = cashTransaction;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
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
    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    @JsonProperty
    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @JsonIgnore
    public Long getTransactionCategoryId() {
        return transactionCategoryId;
    }

    @JsonProperty
    public void setTransactionCategoryId(Long transactionCategoryId) {
        this.transactionCategoryId = transactionCategoryId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @JsonIgnore
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    @JsonIgnore
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty
    public Receipt getReceipt() {
        return receipt;
    }

    @JsonIgnore
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
