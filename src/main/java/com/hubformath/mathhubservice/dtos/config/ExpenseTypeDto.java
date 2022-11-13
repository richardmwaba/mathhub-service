package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.ExpenseDto;

public class ExpenseTypeDto {
    private Long id;

    private String name;

    private String description;

    private ExpenseDto expense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return name;
    }

    public void setTypeName(String name) {
        this.name = name;
    }

    public String getTypeDescription() {
        return description;
    }

    public void setTypeDescription(String description) {
        this.description = description;
    }

    public ExpenseDto getExpense() {
        return expense;
    }

    public void setExpense(ExpenseDto expense) {
        this.expense = expense;
    }
}
