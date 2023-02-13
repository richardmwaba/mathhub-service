package com.hubformath.mathhubservice.service.ops.cashbook;
import java.util.List;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;

public interface IAssetService {
    List<Asset> getAllAssets();

    Asset createAsset(Asset asset);

    Asset getAssetById(Long id);

    Asset updateAsset(Long id, Asset asset);

    void deleteAsset(Long id);
}
