package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

import java.time.LocalDate;
import java.util.Set;

public class TuitionPaymentDto {

    private String tuitionPaymentId;

    private CashTransactionDto cashTransaction;

    private StudentRequest student;

    private String studentId;

    private Set<String> lessonsIds;

    private String paymentMethodId;

    private PaymentMethodDto paymentMethod;

    private LocalDate paymentDate;

    private Double amount;

    private String invoiceId;

    private ReceiptDto receipt;

    private String narration;

    public String getTuitionPaymentId() {
        return tuitionPaymentId;
    }

    public void setTuitionPaymentId(String tuitionPaymentId) {
        this.tuitionPaymentId = tuitionPaymentId;
    }

    public CashTransactionDto getCashTransaction() {
        return cashTransaction;
    }

    public void setCashTransaction(CashTransactionDto cashTransaction) {
        this.cashTransaction = cashTransaction;
    }

    public StudentRequest getStudent() {
        return student;
    }

    public void setStudent(StudentRequest student) {
        this.student = student;
    }

    @JsonIgnore
    public String getStudentId() {
        return studentId;
    }

    @JsonProperty
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @JsonIgnore
    public Set<String> getLessonsIds() {
        return lessonsIds;
    }

    @JsonProperty
    public void setLessonsIds(Set<String> lessonsIds) {
        this.lessonsIds = lessonsIds;
    }

    @JsonIgnore
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    @JsonProperty
    public void setPaymentMethodId(String paymentMethodId) {
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

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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
