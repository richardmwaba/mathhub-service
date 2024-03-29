package com.hubformath.mathhubservice.model.ops.cashbook;

public record EquityRequest(String paymentMethodId,
                            String narration,
                            String equityTypeId,
                            Double amount) {
}
