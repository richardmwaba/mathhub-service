package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.AssetDto;

public class AssetTypeDto {
    private Long id;

    private String name;

    private String description;

    private AssetDto asset;

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

    public AssetDto getAsset() {
        return asset;
    }

    public void setAsset(AssetDto asset) {
        this.asset = asset;
    }
}
