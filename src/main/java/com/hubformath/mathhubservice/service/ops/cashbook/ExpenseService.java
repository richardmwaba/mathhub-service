package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;
import java.util.Optional;

import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.dto.ops.cashbook.ExpenseRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Expense;
import com.hubformath.mathhubservice.model.ops.cashbook.ExpenseStatus;
import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.ExpenseRepository;
import com.hubformath.mathhubservice.service.systemconfig.IExpenseTypeService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class ExpenseService {

        private final IExpenseTypeService expenseTypeService;

        private final PaymentMethodService paymentMethodService;

        private final ExpenseRepository expenseRepository;

        private final String notFoundItemName;

        @Autowired
        public ExpenseService(final IExpenseTypeService expenseTypeService, final PaymentMethodService paymentMethodService, final ExpenseRepository expenseRepository) {
                super();
                this.expenseTypeService = expenseTypeService;
                this.paymentMethodService = paymentMethodService;
                this.expenseRepository = expenseRepository;
                this.notFoundItemName = "expense";
        }

        public List<Expense> getAllExpenses() {
                return expenseRepository.findAll();
        }

        public Expense getExpenseById(Long id) {
                Optional<Expense> expense = expenseRepository.findById(id);

                if(expense.isPresent()){
                        return expense.get();
                } else {
                        throw new ItemNotFoundException(id, notFoundItemName);
                }
        }

        public Expense createExpense(ExpenseRequestDto expenseRequest) {
                final long expenseTypeId = expenseRequest.getExpenseTypeId();
                final long paymentMethodId = expenseRequest.getPaymentMethodId();
                final Double amount = expenseRequest.getAmount();
                final String narration = expenseRequest.getNarration();
                final ExpenseStatus expenseStatus = expenseRequest.getStatus();

                final ExpenseType expenseType = expenseTypeService.getExpenseTypeById(expenseTypeId);
                final PaymentMethod paymentMethod =  paymentMethodService.getPaymentMethodById(paymentMethodId);

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
                        .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
        }

        public void deleteExpense(Long id) {
                Expense expense = expenseRepository.findById(id)
                        .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

                expenseRepository.delete(expense);
        }
}