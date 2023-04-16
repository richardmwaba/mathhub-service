package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.IncomeRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.IncomeRepository;
import com.hubformath.mathhubservice.service.systemconfig.IncomeTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncomeService {

    private final PaymentMethodService paymentMethodService;

    private final IncomeTypeService incomeTypeService;

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(final PaymentMethodService paymentMethodService, final IncomeTypeService incomeTypeService, final IncomeRepository incomeRepository) {
        this.paymentMethodService = paymentMethodService;
        this.incomeTypeService = incomeTypeService;
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(UUID incomeId) {
        return incomeRepository.findById(incomeId).orElseThrow();
    }

    public Income createIncome(IncomeRequestDto incomeRequest) {
        final UUID paymentMethodId = incomeRequest.getPaymentMethodId();
        final UUID incomeTypeId = incomeRequest.getIncomeTypeId();
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

    public Income updateIncome(UUID incomeId, Income incomeRequest) {
        return incomeRepository.findById(incomeId)
                .map(income -> {
                    Optional.ofNullable(incomeRequest.getNarration()).ifPresent(income::setNarration);
                    Optional.ofNullable(incomeRequest.getPaymentMethod()).ifPresent(income::setPaymentMethod);
                    Optional.ofNullable(incomeRequest.getIncomeType()).ifPresent(income::setIncomeType);
                    Optional.ofNullable(incomeRequest.getAmount()).ifPresent(income::setAmount);
                    return incomeRepository.save(income);
                })
                .orElseThrow();
    }

    public void deleteIncome(UUID incomeId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow();

        incomeRepository.delete(income);
    }
}