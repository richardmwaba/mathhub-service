package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.sis.Lesson;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

import java.time.LocalDate;

public class TuitionPaymentDto {
    private Long id;

    private CashTransaction cashTransaction;

    private Student student;

    private Lesson lesson;

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

    public Lesson getLessons() {
        return lesson;
    }

    public void setLessons(Lesson lesson) {
        this.lesson = lesson;
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
}
