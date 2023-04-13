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
import java.util.UUID;

@Service
public class ExpenseService {

    private final PaymentMethodService paymentMethodService;

    private final ExpenseTypeService expenseTypeService;

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(final PaymentMethodService paymentMethodService, ExpenseTypeService expenseTypeService, final ExpenseRepository expenseRepository) {
        this.paymentMethodService = paymentMethodService;
        this.expenseTypeService = expenseTypeService;
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow();

    }

    public Expense createExpense(ExpenseRequestDto expenseRequest) {
        final UUID expenseTypeId = expenseRequest.getExpenseTypeId();
        final UUID paymentMethodId = expenseRequest.getPaymentMethodId();
        final Double amount = expenseRequest.getAmount();
        final String narration = expenseRequest.getNarration();
        final ExpenseStatus expenseStatus = expenseRequest.getStatus();

        final ExpenseType expenseType = expenseTypeService.getExpenseTypeById(expenseTypeId);
        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);

        // To do: Replace null created by with actual logged in user
        final Expense newExpense = new Expense(narration, expenseStatus, amount, null, null);
        newExpense.setExpenseType(expenseType);
        newExpense.setPaymentMethod(paymentMethod);

        return expenseRepository.save(newExpense);
    }

    public Expense updateExpense(Long id, Expense expenseRequest) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setPaymentMethod(expenseRequest.getPaymentMethod());
                    expense.setNarration(expenseRequest.getNarration());
                    expense.setStatus(expenseRequest.getStatus());
                    expense.setExpenseType(expenseRequest.getExpenseType());
                    expense.setAmount(expenseRequest.getAmount());
                    return expenseRepository.save(expense);
                })
                .orElseThrow();
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow();

        expenseRepository.delete(expense);
    }
}