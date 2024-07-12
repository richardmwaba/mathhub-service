package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.systemconfig.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(final PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(String paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId).orElseThrow();
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.save(paymentMethodRequest);
    }

    public PaymentMethod updatePaymentMethod(String paymentMethodId, PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.findById(paymentMethodId)
                                      .map(paymentMethod -> {
                                          Optional.ofNullable(paymentMethodRequest.getName())
                                                  .ifPresent(paymentMethod::setName);
                                          Optional.ofNullable(paymentMethodRequest.getDescription())
                                                  .ifPresent(paymentMethod::setDescription);
                                          return paymentMethodRepository.save(paymentMethod);
                                      })
                                      .orElseThrow();
    }

    public void deletePaymentMethod(String paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                                                             .orElseThrow();

        paymentMethodRepository.delete(paymentMethod);
    }
}
