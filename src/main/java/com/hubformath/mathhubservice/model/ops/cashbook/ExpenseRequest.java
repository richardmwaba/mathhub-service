package com.hubformath.mathhubservice.model.ops.cashbook;

public record ExpenseRequest(String paymentMethodId, String narration, String expenseTypeId, Double amount) {
}
