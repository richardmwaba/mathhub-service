package com.hubformath.mathhubservice.model.ops.cashbook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String receiptNumber;

    private String transactionNumber;

    private LocalDate receiptDate;

    private Long issuedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Receipt(String transactionNumber) {
        this.receiptNumber = this.id.toString();
        this.transactionNumber = transactionNumber;
        this.receiptDate = LocalDate.now();
        this.issuedBy = null;
    }

    public Receipt() {}

    public Long getId() {
        return id;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Receipt))
            return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(this.id, receipt.id) && Objects.equals(this.receiptNumber, receipt.receiptNumber)
                && Objects.equals(this.transactionNumber, receipt.transactionNumber)
                && Objects.equals(this.receiptDate, receipt.receiptDate)
                && Objects.equals(this.issuedBy, receipt.issuedBy)
                && Objects.equals(this.createdAt, receipt.createdAt)
                && Objects.equals(this.updatedAt, receipt.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.receiptNumber, this.transactionNumber, this.receiptDate, this.issuedBy, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Receipt {id=" + this.id + ", receiptNumber=" + this.receiptNumber + ", transactionNumber=" + this.transactionNumber
                + ", receiptDate="
                + this.receiptDate + ", issuedBy=" + this.issuedBy + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }
}