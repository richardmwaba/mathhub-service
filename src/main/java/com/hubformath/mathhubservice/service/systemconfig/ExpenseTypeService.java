package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.ExpenseType;
import com.hubformath.mathhubservice.repository.systemconfig.ExpenseTypeRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;
    private final String notFoundItemName;
    
    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository) {
        super();
        this.expenseTypeRepository = expenseTypeRepository;
        this.notFoundItemName = "expense type";
    }

    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    public ExpenseType getExpenseTypeById(Long id) {
        Optional<ExpenseType> expenseType = expenseTypeRepository.findById(id);

        if(expenseType.isPresent()){
            return expenseType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteExpenseType(Long id) {
        ExpenseType expenseType = expenseTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        expenseTypeRepository.delete(expenseType);
    }
}
