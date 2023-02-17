package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;

import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;


public interface ITuitionPaymentService {
    List<TuitionPayment> getAllTuitionPayments();

    TuitionPayment createTuitionPayment(TuitionPaymentRequestDto tuitionPaymentRequest);

    TuitionPayment getTuitionPaymentById(Long id);
}