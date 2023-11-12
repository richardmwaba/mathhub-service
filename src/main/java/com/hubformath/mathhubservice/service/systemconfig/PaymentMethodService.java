package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.systemconfig.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(final PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(UUID paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId).orElseThrow();
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.save(paymentMethodRequest);
    }

    public PaymentMethod updatePaymentMethod(UUID paymentMethodId, PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.findById(paymentMethodId)
                                      .map(paymentMethod -> {
                                          Optional.ofNullable(paymentMethodRequest.getTypeName())
                                                  .ifPresent(paymentMethod::setTypeName);
                                          Optional.ofNullable(paymentMethodRequest.getTypeDescription())
                                                  .ifPresent(paymentMethod::setTypeDescription);
                                          return paymentMethodRepository.save(paymentMethod);
                                      })
                                      .orElseThrow();
    }

    public void deletePaymentMethod(UUID paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                                                             .orElseThrow();

        paymentMethodRepository.delete(paymentMethod);
    }
}
