package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.repository.systemconfig.CashTransactionCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashTransactionCategoryService {

    private final CashTransactionCategoryRepository cashTransactionCategoryRepository;

    public CashTransactionCategoryService(final CashTransactionCategoryRepository cashTransactionCategoryRepository) {
        this.cashTransactionCategoryRepository = cashTransactionCategoryRepository;
    }

    public List<CashTransactionCategory> getAllCashTransactionCategories() {
        return cashTransactionCategoryRepository.findAll();
    }

    public CashTransactionCategory getCashTransactionCategoryById(String cashTransactionCategoryId) {
        return cashTransactionCategoryRepository.findById(cashTransactionCategoryId).orElseThrow();
    }

    public CashTransactionCategory createCashTransactionCategory(CashTransactionCategory cashTransactionCategoryRequest) {
        return cashTransactionCategoryRepository.save(cashTransactionCategoryRequest);
    }

    public CashTransactionCategory updateCashTransactionCategory(String cashTransactionCategoryId,
                                                                 CashTransactionCategory cashTransactionCategoryRequest) {
        return cashTransactionCategoryRepository.findById(cashTransactionCategoryId)
                                                .map(cashTransactionCategory -> {
                                                    Optional.ofNullable(cashTransactionCategoryRequest.getCategoryName())
                                                            .ifPresent(cashTransactionCategory::setCategoryName);
                                                    Optional.ofNullable(cashTransactionCategoryRequest.getCategoryDescription())
                                                            .ifPresent(cashTransactionCategory::setCategoryDescription);
                                                    return cashTransactionCategoryRepository.save(
                                                            cashTransactionCategory);
                                                })
                                                .orElseThrow();
    }

    public void deleteCashTransactionCategory(String cashTransactionCategoryId) {
        CashTransactionCategory cashTransactionCategory = cashTransactionCategoryRepository.findById(
                                                                                                   cashTransactionCategoryId)
                                                                                           .orElseThrow();

        cashTransactionCategoryRepository.delete(cashTransactionCategory);
    }
}