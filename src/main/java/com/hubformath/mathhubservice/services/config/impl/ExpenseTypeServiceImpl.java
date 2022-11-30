package com.hubformath.mathhubservice.services.config.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.config.ExpenseType;
import com.hubformath.mathhubservice.repositories.config.ExpenseTypeRepository;
import com.hubformath.mathhubservice.services.config.IExpenseTypeService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class ExpenseTypeServiceImpl implements IExpenseTypeService{
    private final ExpenseTypeRepository expenseTypeRepository;
    private final String notFoundItemName;
    
    public ExpenseTypeServiceImpl(ExpenseTypeRepository expenseTypeRepository) {
        super();
        this.expenseTypeRepository = expenseTypeRepository;
        this.notFoundItemName = "expense type";
    }

    @Override
    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    @Override
    public ExpenseType getExpenseTypeById(Long id) {
        Optional<ExpenseType> expenseType = expenseTypeRepository.findById(id);

        if(expenseType.isPresent()){
            return expenseType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public ExpenseType createExpenseType(ExpenseType expenseTypeRequest) {
        return expenseTypeRepository.save(expenseTypeRequest);
    }

    @Override
    public ExpenseType updateExpenseType(Long id, ExpenseType expenseTypeRequest) {
        return expenseTypeRepository.findById(id) 
                .map(expenseType -> {
                    expenseType.setTypeName(expenseTypeRequest.getTypeName());
                    expenseType.setTypeDescription(expenseTypeRequest.getTypeDescription());
                    return expenseTypeRepository.save(expenseType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteExpenseType(Long id) {
        ExpenseType expenseType = expenseTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        expenseTypeRepository.delete(expenseType);
    }
}
