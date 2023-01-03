package com.hubformath.mathhubservice.services.ops.cashbook.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.ops.cashbook.Expense;
import com.hubformath.mathhubservice.repositories.ops.cashbook.ExpenseRepository;
import com.hubformath.mathhubservice.services.ops.cashbook.IExpenseService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class ExpenseServiceImpl implements IExpenseService{
    private final ExpenseRepository expenseRepository;
    private final String notFoundItemName;
    
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        super();
        this.expenseRepository = expenseRepository;
        this.notFoundItemName = "expense";
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if(expense.isPresent()){
            return expense.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Expense createExpense(Expense expenseRequest) {
        return expenseRepository.save(expenseRequest);
    }

    @Override
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        expenseRepository.delete(expense);
    }
}

