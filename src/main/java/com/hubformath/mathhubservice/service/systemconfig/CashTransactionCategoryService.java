package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.repository.systemconfig.CashTransactionCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashTransactionCategoryService {

    private final CashTransactionCategoryRepository cashTransactionCategoryRepository;

    public CashTransactionCategoryService(final CashTransactionCategoryRepository cashTransactionCategoryRepository) {
        super();
        this.cashTransactionCategoryRepository = cashTransactionCategoryRepository;
    }

    public List<CashTransactionCategory> getAllCashTransactionCategories() {
        return cashTransactionCategoryRepository.findAll();
    }

    public CashTransactionCategory getCashTransactionCategoryById(Long id) {
        return cashTransactionCategoryRepository.findById(id).orElseThrow();
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
                .orElseThrow();
    }

    public void deleteCashTransactionCategory(Long id) {
        CashTransactionCategory cashTransactionCategory = cashTransactionCategoryRepository.findById(id)
                .orElseThrow();

        cashTransactionCategoryRepository.delete(cashTransactionCategory);
    }
}