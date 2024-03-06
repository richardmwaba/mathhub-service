package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.repository.ops.cashbook.LiabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiabilityService {

    private final LiabilityRepository liabilityRepository;

    @Autowired
    public LiabilityService(final LiabilityRepository liabilityRepository) {
        this.liabilityRepository = liabilityRepository;
    }

    public List<Liability> getAllLiabilities() {
        return liabilityRepository.findAll();
    }

    public Liability getLiabilityById(String liabilityId) {
        return liabilityRepository.findById(liabilityId).orElseThrow();
    }

    public Liability createLiability(Liability liabilityRequest) {
        return liabilityRepository.save(liabilityRequest);
    }

    public Liability updateLiability(String liabilityId, Liability liabilityRequest) {
        return liabilityRepository.findById(liabilityId)
                                  .map(liability -> {
                                      Optional.ofNullable(liabilityRequest.getPaymentMethod())
                                              .ifPresent(liability::setPaymentMethod);
                                      Optional.ofNullable(liabilityRequest.getLiabilityType())
                                              .ifPresent(liability::setLiabilityType);
                                      Optional.ofNullable(liabilityRequest.getAmount()).ifPresent(liability::setAmount);
                                      return liabilityRepository.save(liability);
                                  })
                                  .orElseThrow();
    }

    public void deleteLiability(String liabilityId) {
        Liability liability = liabilityRepository.findById(liabilityId)
                                                 .orElseThrow();

        liabilityRepository.delete(liability);
    }
}