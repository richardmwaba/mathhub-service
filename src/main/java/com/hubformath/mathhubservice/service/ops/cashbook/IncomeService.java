package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Income;
import com.hubformath.mathhubservice.model.ops.cashbook.IncomeRequest;
import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.IncomeRepository;
import com.hubformath.mathhubservice.service.systemconfig.IncomeTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final PaymentMethodService paymentMethodService;

    private final IncomeTypeService incomeTypeService;

    private final IncomeRepository incomeRepository;

    private final UsersService usersService;

    @Autowired
    public IncomeService(final PaymentMethodService paymentMethodService,
                         final IncomeTypeService incomeTypeService,
                         final IncomeRepository incomeRepository, UsersService usersService) {
        this.paymentMethodService = paymentMethodService;
        this.incomeTypeService = incomeTypeService;
        this.incomeRepository = incomeRepository;
        this.usersService = usersService;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(String incomeId) {
        return incomeRepository.findById(incomeId).orElseThrow();
    }

    public Income createIncome(IncomeRequest incomeRequest) {
        final String paymentMethodId = incomeRequest.paymentMethodId();
        final String incomeTypeId = incomeRequest.incomeTypeId();
        final String narration = incomeRequest.narration();
        final Double amount = incomeRequest.amount();

        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
        final IncomeType incomeType = incomeTypeService.getIncomeTypeById(incomeTypeId);

        final Income newIncome = new Income(paymentMethod,
                                            narration,
                                            incomeType,
                                            amount,
                                            usersService.getLoggedInUser());

        return incomeRepository.save(newIncome);
    }

    public Income updateIncome(String incomeId, IncomeRequest incomeRequest) {
        return incomeRepository.findById(incomeId)
                               .map(income -> {
                                   Optional.ofNullable(incomeRequest.narration()).ifPresent(income::setNarration);
                                   Optional.ofNullable(incomeRequest.paymentMethodId())
                                           .ifPresent(paymentMethodId -> {
                                               PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
                                               income.setPaymentMethod(paymentMethod);
                                           });
                                   Optional.ofNullable(incomeRequest.incomeTypeId()).ifPresent(incomeTypeId -> {
                                       IncomeType incomeType = incomeTypeService.getIncomeTypeById(incomeTypeId);
                                       income.setIncomeType(incomeType);
                                   });
                                   Optional.ofNullable(incomeRequest.amount()).ifPresent(income::setAmount);
                                   return incomeRepository.save(income);
                               })
                               .orElseThrow();
    }

    public void deleteIncome(String incomeId) {
        Income income = incomeRepository.findById(incomeId)
                                        .orElseThrow();

        incomeRepository.delete(income);
    }
}