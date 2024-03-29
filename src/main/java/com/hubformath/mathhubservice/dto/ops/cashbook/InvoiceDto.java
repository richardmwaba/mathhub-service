package com.hubformath.mathhubservice.dto.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.model.sis.StudentRequest;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceStatus;

import java.time.LocalDate;

public class InvoiceDto {

    private String invoiceId;

    private String invoiceNumber;

    private StudentRequest student;

    private String studentId;

    private LocalDate invoiceDate;

    private Double amount;

    private String narration;

    private InvoiceStatus invoiceStatus;

    private LocalDate dueDate;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
