package com.hubformath.mathhubservice.service.ops.cashbook.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.repository.ops.cashbook.LiabilityRepository;
import com.hubformath.mathhubservice.service.ops.cashbook.ILiabilityService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class LiabilityServiceImpl implements ILiabilityService{
    private final LiabilityRepository liabilityRepository;
    private final String notFoundItemName;

    public LiabilityServiceImpl(LiabilityRepository liabilityRepository) {
        super();
        this.liabilityRepository = liabilityRepository;
        this.notFoundItemName = "liability";
    }

    @Override
    public List<Liability> getAllLiabilities() {
        return liabilityRepository.findAll();
    }

    @Override
    public Liability getLiabilityById(Long id) {
        Optional<Liability> liability = liabilityRepository.findById(id);

        if(liability.isPresent()){
            return liability.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Liability createLiability(Liability liabilityRequest) {
        return liabilityRepository.save(liabilityRequest);
    }

    @Override
    public Liability updateLiability(Long id, Liability liabilityRequest) {
        return liabilityRepository.findById(id)
                .map(liability -> {
                    liability.setPaymentMethod(liabilityRequest.getPaymentMethod());
                    liability.setLiabilityType(liabilityRequest.getLiabilityType());
                    liability.setAmount(liabilityRequest.getAmount());
                    return liabilityRepository.save(liability);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteLiability(Long id) {
        Liability liability = liabilityRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        liabilityRepository.delete(liability);
    }
}

    
