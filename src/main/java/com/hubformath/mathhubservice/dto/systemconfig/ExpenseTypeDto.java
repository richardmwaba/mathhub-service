package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class ExpenseTypeDto {
    private UUID expenseTypeId;

    private String typeName;

    private String typeDescription;


    public UUID getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(UUID expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

}
