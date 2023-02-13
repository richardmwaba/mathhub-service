package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;

public interface IPaymentMethodService {
    public List<PaymentMethod> getAllPaymentMethods();

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);

    public PaymentMethod getPaymentMethodById(Long id);

    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod);

    public void deletePaymentMethod(Long id);
}
