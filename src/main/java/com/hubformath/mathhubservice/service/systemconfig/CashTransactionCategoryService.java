package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.repository.systemconfig.CashTransactionCategoryRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class CashTransactionCategoryService {

    private final CashTransactionCategoryRepository cashTransactionCategoryRepository;

    private final String notFoundItemName;
    
    public CashTransactionCategoryService(final CashTransactionCategoryRepository cashTransactionCategoryRepository) {
        super();
        this.cashTransactionCategoryRepository = cashTransactionCategoryRepository;
        this.notFoundItemName = "cash transaction category";
    }

    public List<CashTransactionCategory> getAllCashTransactionCategories() {
        return cashTransactionCategoryRepository.findAll();
    }

    public CashTransactionCategory getCashTransactionCategoryById(Long id) {
        Optional<CashTransactionCategory> cashTransactionCategory = cashTransactionCategoryRepository.findById(id);

        if(cashTransactionCategory.isPresent()){
            return cashTransactionCategory.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public CashTransactionCategory createCashTransactionCategory(CashTransactionCategory cashTransactionCategoryRequest) {
        return cashTransactionCategoryRepository.save(cashTransactionCategoryRequest);
    }

    public CashTransactionCategory updateCashTransactionCategory(Long id, CashTransactionCategory cashTransactionCategoryRequest) {
        return cashTransactionCategoryRepository.findById(id) 
                .map(cashTransactionCategory -> {
                    cashTransactionCategory.setCategoryName(cashTransactionCategoryRequest.getCategoryName());
                    cashTransactionCategory.setCategoryDescription(cashTransactionCategoryRequest.getCategoryDescription());
                    return cashTransactionCategoryRepository.save(cashTransactionCategory);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteCashTransactionCategory(Long id) {
        CashTransactionCategory cashTransactionCategory = cashTransactionCategoryRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        cashTransactionCategoryRepository.delete(cashTransactionCategory);
    }
}