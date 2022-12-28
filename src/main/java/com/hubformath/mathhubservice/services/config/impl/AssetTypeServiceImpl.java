package com.hubformath.mathhubservice.services.config.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.config.AssetType;
import com.hubformath.mathhubservice.repositories.config.AssetTypeRepository;
import com.hubformath.mathhubservice.services.config.IAssetTypeService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class AssetTypeServiceImpl implements IAssetTypeService{
    private final AssetTypeRepository assetTypeRepository;
    private final String notFoundItemName;
    
    public AssetTypeServiceImpl(AssetTypeRepository assetTypeRepository) {
        super();
        this.assetTypeRepository = assetTypeRepository;
        this.notFoundItemName = "asset type";
    }

    @Override
    public List<AssetType> getAllAssetTypes() {
        return assetTypeRepository.findAll();
    }

    @Override
    public AssetType getAssetTypeById(Long id) {
        Optional<AssetType> assetType = assetTypeRepository.findById(id);

        if(assetType.isPresent()){
            return assetType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public AssetType createAssetType(AssetType assetTypeRequest) {
        return assetTypeRepository.save(assetTypeRequest);
    }

    @Override
    public AssetType updateAssetType(Long id, AssetType assetTypeRequest) {
        return assetTypeRepository.findById(id) 
                .map(assetType -> {
                    assetType.setTypeName(assetTypeRequest.getTypeName());
                    assetType.setTypeDescription(assetTypeRequest.getTypeDescription());
                    return assetTypeRepository.save(assetType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteAssetType(Long id) {
        AssetType assetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        assetTypeRepository.delete(assetType);
    }
    
}
