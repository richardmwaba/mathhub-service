package com.hubformath.mathhubservice.service.ops.cashbook.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import com.hubformath.mathhubservice.repository.ops.cashbook.AssetRepository;
import com.hubformath.mathhubservice.service.ops.cashbook.IAssetService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class AssetServiceImpl implements IAssetService{
    private final AssetRepository assetRepository;
    private final String notFoundItemName;
    
    public AssetServiceImpl(AssetRepository assetRepository) {
        super();
        this.assetRepository = assetRepository;
        this.notFoundItemName = "asset";
    }

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    @Override
    public Asset getAssetById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);

        if(asset.isPresent()){
            return asset.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Asset createAsset(Asset assetRequest) {
        return assetRepository.save(assetRequest);
    }

    @Override
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

    @Override
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        assetRepository.delete(asset);
    }
}
