package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;

public interface IAssetTypeService {
    public List<AssetType> getAllAssetTypes();

    public AssetType createAssetType(AssetType assetType);

    public AssetType getAssetTypeById(Long id);

    public AssetType updateAssetType(Long id, AssetType assetType);

    public void deleteAssetType(Long id);
}
