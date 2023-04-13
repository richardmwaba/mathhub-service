package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class IncomeTypeDto {
    private UUID incomeTypeId;

    private String typeName;

    private String typeDescription;

    public UUID getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(UUID incomeTypeId) {
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
