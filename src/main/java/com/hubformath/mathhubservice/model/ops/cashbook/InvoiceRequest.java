package com.hubformath.mathhubservice.model.ops.cashbook;

public record InvoiceRequest(String studentId, Double amount, String narration, InvoiceStatus invoiceStatus) {
}
