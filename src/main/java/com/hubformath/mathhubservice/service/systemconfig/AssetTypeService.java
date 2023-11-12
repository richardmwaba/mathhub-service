package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.repository.systemconfig.AssetTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public AssetType getAssetTypeById(UUID assetTypeId) {
        return assetTypeRepository.findById(assetTypeId).orElseThrow();
    }

    public AssetType createAssetType(AssetType assetTypeRequest) {
        return assetTypeRepository.save(assetTypeRequest);
    }

    public AssetType updateAssetType(UUID assetTypeId, AssetType assetTypeRequest) {
        return assetTypeRepository.findById(assetTypeId)
                                  .map(assetType -> {
                                      Optional.ofNullable(assetTypeRequest.getTypeName())
                                              .ifPresent(assetType::setTypeName);
                                      Optional.ofNullable(assetTypeRequest.getTypeDescription())
                                              .ifPresent(assetType::setTypeDescription);
                                      return assetTypeRepository.save(assetType);
                                  })
                                  .orElseThrow();
    }

    public void deleteAssetType(UUID assetTypeId) {
        AssetType assetType = assetTypeRepository.findById(assetTypeId)
                                                 .orElseThrow();

        assetTypeRepository.delete(assetType);
    }

}
