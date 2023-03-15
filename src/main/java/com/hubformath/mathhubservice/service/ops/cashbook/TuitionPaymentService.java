package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.repository.ops.cashbook.TuitionPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;

    @Autowired
    public TuitionPaymentService(final TuitionPaymentRepository tuitionPaymentRepository) {
        this.tuitionPaymentRepository = tuitionPaymentRepository;
    }

    public List<TuitionPayment> getAllTuitionPayments() {
        return tuitionPaymentRepository.findAll();
    }

    public TuitionPayment getTuitionPaymentById(Long id) {
        return tuitionPaymentRepository.findById(id).orElseThrow();
    }

    @Transactional
    public TuitionPayment createTuitionPayment(TuitionPayment tuitionPayment) {
        return tuitionPaymentRepository.save(tuitionPayment);
    }
}