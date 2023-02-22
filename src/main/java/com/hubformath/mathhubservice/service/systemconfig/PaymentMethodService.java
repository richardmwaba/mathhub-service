package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.systemconfig.PaymentMethodRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    private final String notFoundItemName;
    
    public PaymentMethodService(final PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.notFoundItemName = "payment method";
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(Long id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);

        if(paymentMethod.isPresent()){
            return paymentMethod.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deletePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        paymentMethodRepository.delete(paymentMethod);
    }
}
