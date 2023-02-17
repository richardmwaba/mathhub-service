package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.repository.ops.cashbook.EquityRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class EquityService {

    private final EquityRepository equityRepository;

    private final String notFoundItemName;

    @Autowired
    public EquityService(final EquityRepository equityRepository) {
        super();
        this.equityRepository = equityRepository;
        this.notFoundItemName = "equity";
    }

    public List<Equity> getAllEquity() {
        return equityRepository.findAll();
    }

    public Equity getEquityById(Long id) {
        Optional<Equity> equity = equityRepository.findById(id);

        if(equity.isPresent()){
            return equity.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteEquity(Long id) {
        Equity equity = equityRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        equityRepository.delete(equity);
    }
}
