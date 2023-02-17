package com.hubformath.mathhubservice.service.ops.cashbook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.repository.ops.cashbook.AssetRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    private final String notFoundItemName;

    @Autowired
    public AssetService(final AssetRepository assetRepository) {
        super();
        this.assetRepository = assetRepository;
        this.notFoundItemName = "asset";
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset getAssetById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);

        if(asset.isPresent()){
            return asset.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        assetRepository.delete(asset);
    }
}
