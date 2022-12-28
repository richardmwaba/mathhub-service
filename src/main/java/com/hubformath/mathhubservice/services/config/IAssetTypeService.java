package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.AssetType;

public interface IAssetTypeService {
    public List<AssetType> getAllAssetTypes();

    public AssetType createAssetType(AssetType assetType);

    public AssetType getAssetTypeById(Long id);

    public AssetType updateAssetType(Long id, AssetType assetType);

    public void deleteAssetType(Long id);
}
