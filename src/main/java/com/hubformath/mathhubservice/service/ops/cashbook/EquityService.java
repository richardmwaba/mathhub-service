package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.repository.ops.cashbook.EquityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Equity getEquityById(Long id) {
        return equityRepository.findById(id).orElseThrow();
    }

    public Equity createEquity(Equity equityRequest) {
        return equityRepository.save(equityRequest);
    }

    public Equity updateEquity(Long id, Equity equityRequest) {
        return equityRepository.findById(id)
                .map(equity -> {
                    equity.setPaymentMethod(equityRequest.getPaymentMethod());
                    equity.setNarration(equityRequest.getNarration());
                    equity.setEquityType(equityRequest.getEquityType());
                    equity.setAmount(equityRequest.getAmount());
                    return equityRepository.save(equity);
                })
                .orElseThrow();
    }

    public void deleteEquity(Long id) {
        Equity equity = equityRepository.findById(id)
                .orElseThrow();

        equityRepository.delete(equity);
    }
}
