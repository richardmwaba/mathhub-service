package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.repository.systemconfig.ExpenseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    public ExpenseType getExpenseTypeById(UUID expenseTypeId) {
        return expenseTypeRepository.findById(expenseTypeId).orElseThrow();
    }

    public ExpenseType createExpenseType(ExpenseType expenseTypeRequest) {
        return expenseTypeRepository.save(expenseTypeRequest);
    }

    public ExpenseType updateExpenseType(UUID expenseTypeId, ExpenseType expenseTypeRequest) {
        return expenseTypeRepository.findById(expenseTypeId)
                                    .map(expenseType -> {
                                        Optional.ofNullable(expenseTypeRequest.getTypeName())
                                                .ifPresent(expenseType::setTypeName);
                                        Optional.ofNullable(expenseTypeRequest.getTypeDescription())
                                                .ifPresent(expenseType::setTypeDescription);
                                        return expenseTypeRepository.save(expenseType);
                                    })
                                    .orElseThrow();
    }

    public void deleteExpenseType(UUID expenseTypeId) {
        ExpenseType expenseType = expenseTypeRepository.findById(expenseTypeId)
                                                       .orElseThrow();

        expenseTypeRepository.delete(expenseType);
    }
}
