package com.hubformath.mathhubservice.model.ops.cashbook;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InvoiceStatus {
    PENDING("Pending"),
    PAID("Paid"),
    CANCELLED("Cancelled"),
    OVERDUE("Overdue");

    private final String status;

    InvoiceStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    public static InvoiceStatus fromStatus(String status) {
        for (InvoiceStatus invoiceStatus : InvoiceStatus.values()) {
            if (invoiceStatus.status.equalsIgnoreCase(status)) {
                return invoiceStatus;
            }
        }
        throw new IllegalArgumentException("No constant with status " + status + " found");
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
