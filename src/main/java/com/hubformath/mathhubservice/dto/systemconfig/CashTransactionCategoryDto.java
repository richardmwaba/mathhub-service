package com.hubformath.mathhubservice.dto.systemconfig;

public class CashTransactionCategoryDto {

    private String cashTransactionCategoryId;

    private String categoryName;

    private String categoryDescription;

    public String getCashTransactionCategoryId() {
        return cashTransactionCategoryId;
    }

    public void setCashTransactionCategoryId(String cashTransactionCategoryId) {
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