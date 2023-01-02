package com.hubformath.mathhubservice.services.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.models.ops.cashbook.Asset;

public interface IAssetService {
    public List<Asset> getAllAssets();

    public Asset createAsset(Asset assetType);

    public Asset getAssetById(Long id);

    public Asset updateAsset(Long id, Asset assetType);

    public void deleteAsset(Long id);
}
