package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;

public interface ICashTransactionCategoryService {
    public List<CashTransactionCategory> getAllCashTransactionCategories();

    public CashTransactionCategory createCashTransactionCategory(CashTransactionCategory cashTransactionCategory);

    public CashTransactionCategory getCashTransactionCategoryById(Long id);

    public CashTransactionCategory updateCashTransactionCategory(Long id, CashTransactionCategory cashTransactionCategory);

    public void deleteCashTransactionCategory(Long id);
}