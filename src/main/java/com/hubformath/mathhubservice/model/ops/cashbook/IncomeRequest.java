package com.hubformath.mathhubservice.model.ops.cashbook;

public record IncomeRequest(String paymentMethodId, String narration, String incomeTypeId ,Double amount) {
}
