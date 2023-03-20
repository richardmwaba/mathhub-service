package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.repository.systemconfig.ExpenseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    public ExpenseType getExpenseTypeById(Long id) {
        return expenseTypeRepository.findById(id).orElseThrow();
    }

    public ExpenseType createExpenseType(ExpenseType expenseTypeRequest) {
        return expenseTypeRepository.save(expenseTypeRequest);
    }

    public ExpenseType updateExpenseType(Long id, ExpenseType expenseTypeRequest) {
        return expenseTypeRepository.findById(id) 
                .map(expenseType -> {
                    expenseType.setTypeName(expenseTypeRequest.getTypeName());
                    expenseType.setTypeDescription(expenseTypeRequest.getTypeDescription());
                    return expenseTypeRepository.save(expenseType);
                }) 
                .orElseThrow();
    }

    public void deleteExpenseType(Long id) {
        ExpenseType expenseType = expenseTypeRepository.findById(id)
                .orElseThrow();

        expenseTypeRepository.delete(expenseType);
    }
}
