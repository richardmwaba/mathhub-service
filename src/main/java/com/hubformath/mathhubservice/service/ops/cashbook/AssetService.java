package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.repository.ops.cashbook.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    @Autowired
    public AssetService(final AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElseThrow();
    }

    public Asset createAsset(Asset assetRequest) {
        return assetRepository.save(assetRequest);
    }

    public Asset updateAsset(Long id, Asset assetRequest) {
        return assetRepository.findById(id)
                .map(asset -> {
                    asset.setPaymentMethod(assetRequest.getPaymentMethod());
                    asset.setNarration(assetRequest.getNarration());
                    asset.setAssetType(assetRequest.getAssetType());
                    asset.setAmount(assetRequest.getAmount());
                    return assetRepository.save(asset);
                })
                .orElseThrow();
    }

    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow();

        assetRepository.delete(asset);
    }
}
