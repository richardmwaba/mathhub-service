package com.hubformath.mathhubservice.model.ops.cashbook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hubformath.mathhubservice.model.sis.Lessons;
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
    @JoinColumn(name = "lessons_id")
    private Lessons lessons;

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

    public TuitionPayment(){};

    public TuitionPayment(LocalDate paymentDate, Double amount, String narration) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.narration = narration;
        this.createdBy = null;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lessons getLessons() {
        return lessons;
    }

    public void setLessons(Lessons lessons) {
        this.lessons = lessons;
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
        if (!(o instanceof TuitionPayment))
            return false;
        TuitionPayment tuitionPayment = (TuitionPayment) o;
        return Objects.equals(this.id, tuitionPayment.id)
                && Objects.equals(this.student, tuitionPayment.student)
                && Objects.equals(this.lessons, tuitionPayment.lessons)
                && Objects.equals(this.paymentMethod, tuitionPayment.paymentMethod)
                && Objects.equals(this.paymentDate, tuitionPayment.paymentDate)
                && Objects.equals(this.amount, tuitionPayment.amount)
                && Objects.equals(this.receipt, tuitionPayment.receipt)
                && Objects.equals(this.narration, tuitionPayment.narration)
                && Objects.equals(this.createdAt, tuitionPayment.createdAt)
                && Objects.equals(this.updatedAt, tuitionPayment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.student, this.lessons, this.paymentDate, this.amount,
                this.paymentMethod, this.receipt, this.narration, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "TuitionPayment {id=" + this.id + ", student=" + this.student + ", lessons="
                + this.lessons + ", paymentMethods=" + this.paymentMethod + ", paymentDate=" + this.paymentDate
                + ", amount=" + this.amount + ", receipt="
                + this.receipt + ", narration=" + this.narration + ", createdAt=" + this.createdAt + ", updatedAt="
                + this.updatedAt + "}";
    }

}