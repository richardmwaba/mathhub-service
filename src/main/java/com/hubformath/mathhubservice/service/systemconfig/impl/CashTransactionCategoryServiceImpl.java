package com.hubformath.mathhubservice.service.systemconfig.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.repository.systemconfig.CashTransactionCategoryRepository;
import com.hubformath.mathhubservice.service.systemconfig.ICashTransactionCategoryService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class CashTransactionCategoryServiceImpl implements ICashTransactionCategoryService {
    private final CashTransactionCategoryRepository cashTransactionCategoryRepository;
    private final String notFoundItemName;
    
    public CashTransactionCategoryServiceImpl(CashTransactionCategoryRepository cashTransactionCategoryRepository) {
        super();
        this.cashTransactionCategoryRepository = cashTransactionCategoryRepository;
        this.notFoundItemName = "cash transaction category";
    }

    @Override
    public List<CashTransactionCategory> getAllCashTransactionCategories() {
        return cashTransactionCategoryRepository.findAll();
    }

    @Override
    public CashTransactionCategory getCashTransactionCategoryById(Long id) {
        Optional<CashTransactionCategory> cashTransactionCategory = cashTransactionCategoryRepository.findById(id);

        if(cashTransactionCategory.isPresent()){
            return cashTransactionCategory.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public CashTransactionCategory createCashTransactionCategory(CashTransactionCategory cashTransactionCategoryRequest) {
        return cashTransactionCategoryRepository.save(cashTransactionCategoryRequest);
    }

    @Override
    public CashTransactionCategory updateCashTransactionCategory(Long id, CashTransactionCategory cashTransactionCategoryRequest) {
        return cashTransactionCategoryRepository.findById(id) 
                .map(cashTransactionCategory -> {
                    cashTransactionCategory.setCategoryName(cashTransactionCategoryRequest.getCategoryName());
                    cashTransactionCategory.setCategoryDescription(cashTransactionCategoryRequest.getCategoryDescription());
                    return cashTransactionCategoryRepository.save(cashTransactionCategory);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteCashTransactionCategory(Long id) {
        CashTransactionCategory cashTransactionCategory = cashTransactionCategoryRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        cashTransactionCategoryRepository.delete(cashTransactionCategory);
    }
}