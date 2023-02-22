package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.repository.ops.cashbook.TuitionPaymentRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;

    private final String notFoundItemName;

    @Autowired
    public TuitionPaymentService(final TuitionPaymentRepository tuitionPaymentRepository) {
        this.tuitionPaymentRepository = tuitionPaymentRepository;
        this.notFoundItemName = "tuition payment";
    }

    public List<TuitionPayment> getAllTuitionPayments() {
        return tuitionPaymentRepository.findAll();
    }

    public TuitionPayment getTuitionPaymentById(Long id) {
        Optional<TuitionPayment> tuitionPayment = tuitionPaymentRepository.findById(id);

        if (tuitionPayment.isPresent()) {
            return tuitionPayment.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Transactional
    public TuitionPayment createTuitionPayment(TuitionPayment tuitionPayment) {
        return tuitionPaymentRepository.save(tuitionPayment);
    }
}