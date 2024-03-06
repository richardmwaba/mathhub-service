package com.hubformath.mathhubservice.dto.systemconfig;

public class IncomeTypeDto {

    private String incomeTypeId;

    private String typeName;

    private String typeDescription;

    public String getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(String incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
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
