package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.repository.ops.cashbook.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Asset getAssetById(UUID assetId) {
        return assetRepository.findById(assetId).orElseThrow();
    }

    public Asset createAsset(Asset assetRequest) {
        return assetRepository.save(assetRequest);
    }

    public Asset updateAsset(UUID assetId, Asset assetRequest) {
        return assetRepository.findById(assetId)
                .map(asset -> {
                    Optional.ofNullable(assetRequest.getPaymentMethod()).ifPresent(asset::setPaymentMethod);
                    Optional.ofNullable(assetRequest.getNarration()).ifPresent(asset::setNarration);
                    Optional.ofNullable(assetRequest.getAssetType()).ifPresent(asset::setAssetType);
                    Optional.ofNullable(assetRequest.getAmount()).ifPresent(asset::setAmount);
                    return assetRepository.save(asset);
                })
                .orElseThrow();
    }

    public void deleteAsset(UUID assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow();

        assetRepository.delete(asset);
    }
}
