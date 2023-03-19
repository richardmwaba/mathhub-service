package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.repository.systemconfig.AssetTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetTypeService {

    private final AssetTypeRepository assetTypeRepository;

    public AssetTypeService(AssetTypeRepository assetTypeRepository) {
        super();
        this.assetTypeRepository = assetTypeRepository;
    }

    public List<AssetType> getAllAssetTypes() {
        return assetTypeRepository.findAll();
    }

    public AssetType getAssetTypeById(Long id) {
        return assetTypeRepository.findById(id).orElseThrow();
    }

    public AssetType createAssetType(AssetType assetTypeRequest) {
        return assetTypeRepository.save(assetTypeRequest);
    }

    public AssetType updateAssetType(Long id, AssetType assetTypeRequest) {
        return assetTypeRepository.findById(id) 
                .map(assetType -> {
                    assetType.setTypeName(assetTypeRequest.getTypeName());
                    assetType.setTypeDescription(assetTypeRequest.getTypeDescription());
                    return assetTypeRepository.save(assetType);
                }) 
                .orElseThrow();
    }

    public void deleteAssetType(Long id) {
        AssetType assetType = assetTypeRepository.findById(id)
                .orElseThrow();

        assetTypeRepository.delete(assetType);
    }
    
}
