package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class AssetTypeDto {
    private UUID assetTypeId;

    private String typeName;

    private String typeDescription;

    public UUID getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(UUID assetTypeId) {
        this.assetTypeId = assetTypeId;
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
