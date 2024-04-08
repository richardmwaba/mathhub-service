package com.hubformath.mathhubservice.model.ops.cashbook;

public record CashTransactionRequest(String paymentMethodId,
                                     CashTransactionType transactionType,
                                     String transactionCategoryId,
                                     String narration,
                                     Double amount) {
}
