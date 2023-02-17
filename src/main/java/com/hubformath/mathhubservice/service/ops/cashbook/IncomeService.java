package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.IncomeRepository;
import com.hubformath.mathhubservice.service.systemconfig.IIncomeTypeService;
import com.hubformath.mathhubservice.service.systemconfig.IPaymentMethodService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class IncomeService {

    private final IPaymentMethodService paymentMethodService;

    private final IIncomeTypeService incomeTypeService;

    private final IncomeRepository incomeRepository;

    private final String notFoundItemName;

    @Autowired
    public IncomeService(final IPaymentMethodService paymentMethodService, final IIncomeTypeService incomeTypeService, final IncomeRepository incomeRepository) {
        super();
        this.paymentMethodService = paymentMethodService;
        this.incomeTypeService = incomeTypeService;
        this.incomeRepository = incomeRepository;
        this.notFoundItemName = "income";
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(Long id) {
        Optional<Income> income = incomeRepository.findById(id);

        if(income.isPresent()){
            return income.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public Income createIncome(IncomeRequestDto incomeRequest) {
        final Long paymentMethodId = incomeRequest.getPaymentMethodId();
        final Long incomeTypeId = incomeRequest.getIncomeTypeId();
        final String narration = incomeRequest.getNarration();
        final Double amount = incomeRequest.getAmount();

        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
        final IncomeType incomeType = incomeTypeService.getIncomeTypeById(incomeTypeId);

        // To do: Replace null created by with actual logged in user
        final Income newIncome = new Income(narration, amount, null, null);
        newIncome.setIncomeType(incomeType);
        newIncome.setPaymentMethod(paymentMethod);

        return incomeRepository.save(newIncome);
    }

    public Income updateIncome(Long id, Income incomeRequest) {
        return incomeRepository.findById(id)
                .map(income -> {
                    income.setNarration(incomeRequest.getNarration());
                    income.setPaymentMethod(incomeRequest.getPaymentMethod());
                    income.setIncomeType(incomeRequest.getIncomeType());
                    income.setAmount(incomeRequest.getAmount());
                    return incomeRepository.save(income);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        incomeRepository.delete(income);
    }
}