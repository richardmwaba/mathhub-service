package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.repository.systemconfig.AssetTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetTypeService {

    private final AssetTypeRepository assetTypeRepository;

    public AssetTypeService(AssetTypeRepository assetTypeRepository) {
        this.assetTypeRepository = assetTypeRepository;
    }

    public List<AssetType> getAllAssetTypes() {
        return assetTypeRepository.findAll();
    }

    public AssetType getAssetTypeById(String assetTypeId) {
        return assetTypeRepository.findById(assetTypeId).orElseThrow();
    }

    public AssetType createAssetType(AssetType assetTypeRequest) {
        return assetTypeRepository.save(assetTypeRequest);
    }

    public AssetType updateAssetType(String assetTypeId, AssetType assetTypeRequest) {
        return assetTypeRepository.findById(assetTypeId)
                                  .map(assetType -> {
                                      Optional.ofNullable(assetTypeRequest.getName())
                                              .ifPresent(assetType::setName);
                                      Optional.ofNullable(assetTypeRequest.getDescription())
                                              .ifPresent(assetType::setDescription);
                                      return assetTypeRepository.save(assetType);
                                  })
                                  .orElseThrow();
    }

    public void deleteAssetType(String assetTypeId) {
        AssetType assetType = assetTypeRepository.findById(assetTypeId)
                                                 .orElseThrow();

        assetTypeRepository.delete(assetType);
    }

}
