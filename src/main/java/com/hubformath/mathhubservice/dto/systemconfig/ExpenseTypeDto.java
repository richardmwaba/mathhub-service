package com.hubformath.mathhubservice.dto.systemconfig;

public class ExpenseTypeDto {

    private String expenseTypeId;

    private String typeName;

    private String typeDescription;


    public String getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(String expenseTypeId) {
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
