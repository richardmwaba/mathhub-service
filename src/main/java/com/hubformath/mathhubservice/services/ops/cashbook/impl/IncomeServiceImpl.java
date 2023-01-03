package com.hubformath.mathhubservice.services.ops.cashbook.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.ops.cashbook.Income;
import com.hubformath.mathhubservice.repositories.ops.cashbook.IncomeRepository;
import com.hubformath.mathhubservice.services.ops.cashbook.IIncomeService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class IncomeServiceImpl implements IIncomeService{
    private final IncomeRepository incomeRepository;
    private final String notFoundItemName;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        super();
        this.incomeRepository = incomeRepository;
        this.notFoundItemName = "income";
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public Income getIncomeById(Long id) {
        Optional<Income> income = incomeRepository.findById(id);

        if(income.isPresent()){
            return income.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Income createIncome(Income incomeRequest) {
        return incomeRepository.save(incomeRequest);
    }

    @Override
    public Income updateIncome(Long id, Income incomeRequest) {
        return incomeRepository.findById(id)
                .map(income -> {
                    income.setPaymentMethod(incomeRequest.getPaymentMethod());
                    income.setIncomeType(incomeRequest.getIncomeType());
                    income.setAmount(incomeRequest.getAmount());
                    return incomeRepository.save(income);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        incomeRepository.delete(income);
    }
}

