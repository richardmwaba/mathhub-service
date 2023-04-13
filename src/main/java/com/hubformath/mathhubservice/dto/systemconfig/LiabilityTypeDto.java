package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class LiabilityTypeDto {
    private UUID liabilityTypeId;

    private String typeName;

    private String typeDescription;

    public UUID getLiabilityTypeId() {
        return liabilityTypeId;
    }

    public void setLiabilityTypeId(UUID liabilityTypeId) {
        this.liabilityTypeId = liabilityTypeId;
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
