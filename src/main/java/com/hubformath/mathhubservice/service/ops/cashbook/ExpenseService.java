package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Expense;
import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseStatus;
import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.ExpenseRepository;
import com.hubformath.mathhubservice.service.systemconfig.ExpenseTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final PaymentMethodService paymentMethodService;

    private final ExpenseTypeService expenseTypeService;

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(final PaymentMethodService paymentMethodService,
                          ExpenseTypeService expenseTypeService,
                          final ExpenseRepository expenseRepository) {
        this.paymentMethodService = paymentMethodService;
        this.expenseTypeService = expenseTypeService;
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(String expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow();

    }

    public Expense createExpense(ExpenseRequestDto expenseRequest) {
        final String expenseTypeId = expenseRequest.getExpenseTypeId();
        final String paymentMethodId = expenseRequest.getPaymentMethodId();
        final Double amount = expenseRequest.getAmount();
        final String narration = expenseRequest.getNarration();
        final ExpenseStatus expenseStatus = expenseRequest.getStatus();

        final ExpenseType expenseType = expenseTypeService.getExpenseTypeById(expenseTypeId);
        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);

        // To do: Replace null created by with actual logged-in user
        final Expense newExpense = new Expense(narration, expenseStatus, amount, null, null);
        newExpense.setExpenseType(expenseType);
        newExpense.setPaymentMethod(paymentMethod);

        return expenseRepository.save(newExpense);
    }

    public Expense updateExpense(String expenseId, Expense expenseRequest) {
        return expenseRepository.findById(expenseId)
                                .map(expense -> {
                                    Optional.ofNullable(expenseRequest.getPaymentMethod())
                                            .ifPresent(expense::setPaymentMethod);
                                    Optional.ofNullable(expenseRequest.getNarration()).ifPresent(expense::setNarration);
                                    Optional.ofNullable(expenseRequest.getStatus()).ifPresent(expense::setStatus);
                                    Optional.ofNullable(expenseRequest.getExpenseType())
                                            .ifPresent(expense::setExpenseType);
                                    Optional.ofNullable(expenseRequest.getAmount()).ifPresent(expense::setAmount);
                                    return expenseRepository.save(expense);
                                })
                                .orElseThrow();
    }

    public void deleteExpense(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                                           .orElseThrow();

        expenseRepository.delete(expense);
    }
}