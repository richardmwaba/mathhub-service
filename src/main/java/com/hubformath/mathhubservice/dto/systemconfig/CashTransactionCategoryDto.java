package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class CashTransactionCategoryDto {
    private UUID cashTransactionCategoryId;

    private String categoryName;

    private String categoryDescription;

    public UUID getCashTransactionCategoryId() {
        return cashTransactionCategoryId;
    }

    public void setCashTransactionCategoryId(UUID cashTransactionCategoryId) {
        this.cashTransactionCategoryId = cashTransactionCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}