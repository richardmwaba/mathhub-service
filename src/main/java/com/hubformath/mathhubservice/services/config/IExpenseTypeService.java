package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.ExpenseType;

public interface IExpenseTypeService {
    public List<ExpenseType> getAllExpenseTypes();

    public ExpenseType createExpenseType(ExpenseType expenseType);

    public ExpenseType getExpenseTypeById(Long id);

    public ExpenseType updateExpenseType(Long id, ExpenseType expenseType);

    public void deleteExpenseType(Long id);
}
