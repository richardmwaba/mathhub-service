package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.systemconfig.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(final PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id).orElseThrow();
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.save(paymentMethodRequest);
    }

    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.findById(id) 
                .map(paymentMethod -> {
                    paymentMethod.setTypeName(paymentMethodRequest.getTypeName());
                    paymentMethod.setTypeDescription(paymentMethodRequest.getTypeDescription());
                    return paymentMethodRepository.save(paymentMethod);
                }) 
                .orElseThrow();
    }

    public void deletePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow();

        paymentMethodRepository.delete(paymentMethod);
    }
}
