package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.sis.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDate invoiceDate;

    private Double amount;

    private String narration;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    private LocalDate dueDate;

    private Long issuedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Invoice() {
    }

    public Invoice(final Double amount, final String narration) {
        this.invoiceNumber = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        this.invoiceDate = LocalDate.now();
        this.amount = amount;
        this.narration = narration;
        this.invoiceStatus = InvoiceStatus.PENDING;
        this.dueDate = LocalDate.now().plusDays(14);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Long issuedBy) {
        this.issuedBy = issuedBy;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;
        return Objects.equals(getId(), invoice.getId())
                && Objects.equals(getInvoiceNumber(), invoice.getInvoiceNumber())
                && Objects.equals(getInvoiceDate(), invoice.getInvoiceDate())
                && Objects.equals(getAmount(), invoice.getAmount())
                && Objects.equals(getNarration(), invoice.getNarration())
                && getInvoiceStatus() == invoice.getInvoiceStatus()
                && Objects.equals(getDueDate(), invoice.getDueDate())
                && Objects.equals(getIssuedBy(), invoice.getIssuedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getInvoiceNumber(),
                getInvoiceDate(),
                getAmount(),
                getNarration(),
                getInvoiceStatus(),
                getDueDate(),
                getIssuedBy());
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", student=" + student +
                ", invoiceDate=" + invoiceDate +
                ", amount=" + amount +
                ", narration='" + narration + '\'' +
                ", invoiceStatus=" + invoiceStatus +
                ", dueDate=" + dueDate +
                ", issuedBy=" + issuedBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
