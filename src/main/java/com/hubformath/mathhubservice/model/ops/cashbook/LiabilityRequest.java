package com.hubformath.mathhubservice.model.ops.cashbook;

public record LiabilityRequest(String liabilityTypeId, String paymentMethodId, Double amount, String narration) {
}
