package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Expense;
import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseRequest;
import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.ExpenseRepository;
import com.hubformath.mathhubservice.service.systemconfig.ExpenseTypeService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final PaymentMethodService paymentMethodService;

    private final ExpenseTypeService expenseTypeService;

    private final ExpenseRepository expenseRepository;

    private final UsersService usersService;

    @Autowired
    public ExpenseService(PaymentMethodService paymentMethodService,
                          ExpenseTypeService expenseTypeService,
                          ExpenseRepository expenseRepository,
                          UsersService usersService) {
        this.paymentMethodService = paymentMethodService;
        this.expenseTypeService = expenseTypeService;
        this.expenseRepository = expenseRepository;
        this.usersService = usersService;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(String expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow();

    }

    public Expense createExpense(ExpenseRequest expenseRequest) {
        final String expenseTypeId = expenseRequest.expenseTypeId();
        final String paymentMethodId = expenseRequest.paymentMethodId();
        final Double amount = expenseRequest.amount();
        final String narration = expenseRequest.narration();

        final ExpenseType expenseType = expenseTypeService.getExpenseTypeById(expenseTypeId);
        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);

        final Expense newExpense = new Expense(paymentMethod,
                                               narration,
                                               expenseType,
                                               amount,
                                               usersService.getLoggedInUser());

        return expenseRepository.save(newExpense);
    }

    public Expense updateExpense(String expenseId, ExpenseRequest expenseRequest) {
        return expenseRepository.findById(expenseId)
                                .map(expense -> {
                                    Optional.ofNullable(expenseRequest.paymentMethodId())
                                            .ifPresent(paymentMethodId -> {
                                                PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(
                                                        paymentMethodId);
                                                expense.setPaymentMethod(paymentMethod);
                                            });
                                    Optional.ofNullable(expenseRequest.narration()).ifPresent(expense::setNarration);
                                    Optional.ofNullable(expenseRequest.expenseTypeId()).ifPresent(expenseTypeId -> {
                                        ExpenseType expenseType = expenseTypeService.getExpenseTypeById(expenseTypeId);
                                        expense.setType(expenseType);
                                    });
                                    Optional.ofNullable(expenseRequest.amount()).ifPresent(expense::setAmount);
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