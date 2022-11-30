package com.hubformath.mathhubservice.services.config.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.config.PaymentMethod;
import com.hubformath.mathhubservice.repositories.config.PaymentMethodRepository;
import com.hubformath.mathhubservice.services.config.IPaymentMethodService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService{
    private final PaymentMethodRepository paymentMethodRepository;
    private final String notFoundItemName;
    
    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        super();
        this.paymentMethodRepository = paymentMethodRepository;
        this.notFoundItemName = "payment method";
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getPaymentMethodById(Long id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);

        if(paymentMethod.isPresent()){
            return paymentMethod.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.save(paymentMethodRequest);
    }

    @Override
    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethodRequest) {
        return paymentMethodRepository.findById(id) 
                .map(paymentMethod -> {
                    paymentMethod.setTypeName(paymentMethodRequest.getTypeName());
                    paymentMethod.setTypeDescription(paymentMethodRequest.getTypeDescription());
                    return paymentMethodRepository.save(paymentMethod);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deletePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        paymentMethodRepository.delete(paymentMethod);
    }
}
