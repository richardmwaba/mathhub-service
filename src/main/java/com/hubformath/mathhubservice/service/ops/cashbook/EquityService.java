package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.repository.ops.cashbook.EquityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquityService {

    private final EquityRepository equityRepository;

    @Autowired
    public EquityService(final EquityRepository equityRepository) {
        this.equityRepository = equityRepository;
    }

    public List<Equity> getAllEquity() {
        return equityRepository.findAll();
    }

    public Equity getEquityById(UUID equityId) {
        return equityRepository.findById(equityId).orElseThrow();
    }

    public Equity createEquity(Equity equityRequest) {
        return equityRepository.save(equityRequest);
    }

    public Equity updateEquity(UUID equityId, Equity equityRequest) {
        return equityRepository.findById(equityId)
                               .map(equity -> {
                                   Optional.ofNullable(equityRequest.getPaymentMethod())
                                           .ifPresent(equity::setPaymentMethod);
                                   Optional.ofNullable(equityRequest.getNarration()).ifPresent(equity::setNarration);
                                   Optional.ofNullable(equityRequest.getEquityType()).ifPresent(equity::setEquityType);
                                   Optional.ofNullable(equityRequest.getAmount()).ifPresent(equity::setAmount);
                                   return equityRepository.save(equity);
                               })
                               .orElseThrow();
    }

    public void deleteEquity(UUID equityId) {
        Equity equity = equityRepository.findById(equityId)
                                        .orElseThrow();

        equityRepository.delete(equity);
    }
}
