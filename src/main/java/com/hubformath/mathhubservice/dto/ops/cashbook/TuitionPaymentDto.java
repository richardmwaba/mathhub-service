package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.dto.sis.StudentDto;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class TuitionPaymentDto {
    private UUID tuitionPaymentId;

    private CashTransactionDto cashTransaction;

    private StudentDto student;

    private UUID studentId;

    private Set<UUID> lessonsIds;

    private UUID paymentMethodId;

    private PaymentMethodDto paymentMethod;

    private LocalDate paymentDate;

    private Double amount;

    private UUID invoiceId;

    private ReceiptDto receipt;

    private String narration;

    public UUID getTuitionPaymentId() {
        return tuitionPaymentId;
    }

    public void setTuitionPaymentId(UUID tuitionPaymentId) {
        this.tuitionPaymentId = tuitionPaymentId;
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
    public UUID getStudentId() {
        return studentId;
    }

    @JsonProperty
    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    @JsonIgnore
    public Set<UUID> getLessonsIds() {
        return lessonsIds;
    }

    @JsonProperty
    public void setLessonsIds(Set<UUID> lessonsIds) {
        this.lessonsIds = lessonsIds;
    }

    @JsonIgnore
    public UUID getPaymentMethodId() {
        return paymentMethodId;
    }

    @JsonProperty
    public void setPaymentMethodId(UUID paymentMethodId) {
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

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID invoiceId) {
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
