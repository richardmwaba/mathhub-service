package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.repository.systemconfig.AssetTypeRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class AssetTypeService {
    private final AssetTypeRepository assetTypeRepository;
    private final String notFoundItemName;
    
    public AssetTypeService(AssetTypeRepository assetTypeRepository) {
        super();
        this.assetTypeRepository = assetTypeRepository;
        this.notFoundItemName = "asset type";
    }

    public List<AssetType> getAllAssetTypes() {
        return assetTypeRepository.findAll();
    }

    public AssetType getAssetTypeById(Long id) {
        Optional<AssetType> assetType = assetTypeRepository.findById(id);

        if(assetType.isPresent()){
            return assetType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteAssetType(Long id) {
        AssetType assetType = assetTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        assetTypeRepository.delete(assetType);
    }
    
}
