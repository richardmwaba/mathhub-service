package com.hubformath.mathhubservice.model.ops.cashbook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

@Entity
public class TuitionPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cash_transaction_id")
    private CashTransaction cashTransaction;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private LocalDate paymentDate;

    private Double amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    private String narration;

    private Long createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public TuitionPayment(){}

    public TuitionPayment(final CashTransaction cashTransaction,
                          final Student student,
                          final PaymentMethod paymentMethod,
                          final Double amount,
                          final Receipt receipt,
                          final String narration) {
        this.cashTransaction = cashTransaction;
        this.student = student;
        this.paymentMethod = paymentMethod;
        this.receipt = receipt;
        this.paymentDate = LocalDate.now();
        this.amount = amount;
        this.narration = narration;
        this.createdBy = null;
    }

    public Long getId() {
        return id;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
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

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TuitionPayment tuitionPayment))
            return false;
        return Objects.equals(this.id, tuitionPayment.id)
                && Objects.equals(this.cashTransaction, tuitionPayment.cashTransaction)
                && Objects.equals(this.student, tuitionPayment.student)
                && Objects.equals(this.paymentMethod, tuitionPayment.paymentMethod)
                && Objects.equals(this.paymentDate, tuitionPayment.paymentDate)
                && Objects.equals(this.amount, tuitionPayment.amount)
                && Objects.equals(this.receipt, tuitionPayment.receipt)
                && Objects.equals(this.narration, tuitionPayment.narration)
                && Objects.equals(this.createdBy, tuitionPayment.createdBy)
                && Objects.equals(this.createdAt, tuitionPayment.createdAt)
                && Objects.equals(this.updatedAt, tuitionPayment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.cashTransaction, this.student, this.paymentDate, this.amount,
                this.paymentMethod, this.receipt, this.narration, this.createdBy, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "TuitionPayment {id=" + this.id + ", cashTransaction=" + this.cashTransaction + ", student=" + this.student
                + ", paymentMethods=" + this.paymentMethod + ", paymentDate=" + this.paymentDate
                + ", amount=" + this.amount + ", receipt="
                + this.receipt + ", narration=" + this.narration + "}";
    }

}