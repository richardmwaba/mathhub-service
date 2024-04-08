package com.hubformath.mathhubservice.model.ops.cashbook;

import java.util.Set;

public record TuitionPaymentRequest(String studentId,
                                    String paymentMethodId,
                                    String invoiceId,
                                    Double amount,
                                    String narration,
                                    Set<String> lessonsIds) {
}
