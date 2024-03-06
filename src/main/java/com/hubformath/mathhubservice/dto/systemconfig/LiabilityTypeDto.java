package com.hubformath.mathhubservice.dto.systemconfig;

public class LiabilityTypeDto {

    private String liabilityTypeId;

    private String typeName;

    private String typeDescription;

    public String getLiabilityTypeId() {
        return liabilityTypeId;
    }

    public void setLiabilityTypeId(String liabilityTypeId) {
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
