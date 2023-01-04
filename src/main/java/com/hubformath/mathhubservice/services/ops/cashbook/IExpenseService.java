package com.hubformath.mathhubservice.services.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.models.ops.cashbook.Expense;

public interface IExpenseService {
        List<Expense> getAllExpenses();

        Expense createExpense(Expense expense);

        Expense getExpenseById(Long id);

        Expense updateExpense(Long id, Expense expense);

        void deleteExpense(Long id);
}
