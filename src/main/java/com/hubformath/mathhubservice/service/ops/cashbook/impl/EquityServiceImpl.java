package com.hubformath.mathhubservice.service.ops.cashbook.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.repository.ops.cashbook.EquityRepository;
import com.hubformath.mathhubservice.service.ops.cashbook.IEquityService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class EquityServiceImpl implements IEquityService {
    private final EquityRepository equityRepository;
    private final String notFoundItemName;

    public EquityServiceImpl(EquityRepository equityRepository) {
        super();
        this.equityRepository = equityRepository;
        this.notFoundItemName = "equity";
    }

    @Override
    public List<Equity> getAllEquity() {
        return equityRepository.findAll();
    }

    @Override
    public Equity getEquityById(Long id) {
        Optional<Equity> equity = equityRepository.findById(id);

        if(equity.isPresent()){
            return equity.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Equity createEquity(Equity equityRequest) {
        return equityRepository.save(equityRequest);
    }

    @Override
    public Equity updateEquity(Long id, Equity equityRequest) {
        return equityRepository.findById(id)
                .map(equity -> {
                    equity.setPaymentMethod(equityRequest.getPaymentMethod());
                    equity.setNarration(equityRequest.getNarration());
                    equity.setEquityType(equityRequest.getEquityType());
                    equity.setAmount(equityRequest.getAmount());
                    return equityRepository.save(equity);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteEquity(Long id) {
        Equity equity = equityRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        equityRepository.delete(equity);
    }
}
