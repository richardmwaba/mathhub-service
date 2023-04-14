package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.repository.systemconfig.LiabilityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LiabilityTypeService {
    private final LiabilityTypeRepository liabilityTypeRepository;

    public LiabilityTypeService(LiabilityTypeRepository liabilityTypeRepository) {
        this.liabilityTypeRepository = liabilityTypeRepository;
    }

    public List<LiabilityType> getAllLiabilityTypes() {
        return liabilityTypeRepository.findAll();
    }

    public LiabilityType getLiabilityTypeById(UUID liabilityTypeId) {
        return liabilityTypeRepository.findById(liabilityTypeId).orElseThrow();
    }

    public LiabilityType createLiabilityType(LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.save(liabilityTypeRequest);
    }

    public LiabilityType updateLiabilityType(UUID liabilityTypeId, LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.findById(liabilityTypeId)
                .map(liabilityType -> {
                    liabilityType.setTypeName(liabilityTypeRequest.getTypeName());
                    liabilityType.setTypeDescription(liabilityTypeRequest.getTypeDescription());
                    return liabilityTypeRepository.save(liabilityType);
                }) 
                .orElseThrow();
    }

    public void deleteLiabilityType(UUID liabilityTypeId) {
        LiabilityType liabilityType = liabilityTypeRepository.findById(liabilityTypeId)
                .orElseThrow();

        liabilityTypeRepository.delete(liabilityType);
    }
}
