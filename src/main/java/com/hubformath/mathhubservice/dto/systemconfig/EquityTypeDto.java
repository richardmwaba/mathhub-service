package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class EquityTypeDto {
    private UUID equityTypeId;

    private String typeName;

    private String typeDescription;


    public UUID getEquityTypeId() {
        return equityTypeId;
    }

    public void setEquityTypeId(UUID equityTypeId) {
        this.equityTypeId = equityTypeId;
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
