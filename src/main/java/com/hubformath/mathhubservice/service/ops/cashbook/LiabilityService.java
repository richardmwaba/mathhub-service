package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.repository.ops.cashbook.LiabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiabilityService{

    private final LiabilityRepository liabilityRepository;

    @Autowired
    public LiabilityService(final LiabilityRepository liabilityRepository) {
        this.liabilityRepository = liabilityRepository;
    }

    public List<Liability> getAllLiabilities() {
        return liabilityRepository.findAll();
    }

    public Liability getLiabilityById(Long id) {
        return liabilityRepository.findById(id).orElseThrow();
    }

    public Liability createLiability(Liability liabilityRequest) {
        return liabilityRepository.save(liabilityRequest);
    }

    public Liability updateLiability(Long id, Liability liabilityRequest) {
        return liabilityRepository.findById(id)
                .map(liability -> {
                    liability.setPaymentMethod(liabilityRequest.getPaymentMethod());
                    liability.setLiabilityType(liabilityRequest.getLiabilityType());
                    liability.setAmount(liabilityRequest.getAmount());
                    return liabilityRepository.save(liability);
                })
                .orElseThrow();
    }

    public void deleteLiability(Long id) {
        Liability liability = liabilityRepository.findById(id)
                .orElseThrow();

        liabilityRepository.delete(liability);
    }
}