package com.hubformath.mathhubservice.dto.ops.cashbook;

import java.time.LocalDate;
import java.util.UUID;

public class ReceiptDto {

    private UUID liabilityId;

    private String receiptNumber;

    private String transactionNumber;

    private LocalDate receiptDate;

    private Long issuedBy;

    public UUID getLiabilityId() {
        return liabilityId;
    }

    public void setLiabilityId(UUID liabilityId) {
        this.liabilityId = liabilityId;
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
}
