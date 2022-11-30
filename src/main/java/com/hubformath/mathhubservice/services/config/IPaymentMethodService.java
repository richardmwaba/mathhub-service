package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.PaymentMethod;

public interface IPaymentMethodService {
    public List<PaymentMethod> getAllPaymentMethods();

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);

    public PaymentMethod getPaymentMethodById(Long id);

    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod);

    public void deletePaymentMethod(Long id);
}
