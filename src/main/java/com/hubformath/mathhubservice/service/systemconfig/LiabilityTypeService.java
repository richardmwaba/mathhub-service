package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.repository.systemconfig.LiabilityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiabilityTypeService {
    private final LiabilityTypeRepository liabilityTypeRepository;

    public LiabilityTypeService(LiabilityTypeRepository liabilityTypeRepository) {
        this.liabilityTypeRepository = liabilityTypeRepository;
    }

    public List<LiabilityType> getAllLiabilityTypes() {
        return liabilityTypeRepository.findAll();
    }

    public LiabilityType getLiabilityTypeById(Long id) {
        return liabilityTypeRepository.findById(id).orElseThrow();
    }

    public LiabilityType createLiabilityType(LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.save(liabilityTypeRequest);
    }

    public LiabilityType updateLiabilityType(Long id, LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.findById(id) 
                .map(liabilityType -> {
                    liabilityType.setTypeName(liabilityTypeRequest.getTypeName());
                    liabilityType.setTypeDescription(liabilityTypeRequest.getTypeDescription());
                    return liabilityTypeRepository.save(liabilityType);
                }) 
                .orElseThrow();
    }

    public void deleteLiabilityType(Long id) {
        LiabilityType liabilityType = liabilityTypeRepository.findById(id)
                .orElseThrow();

        liabilityTypeRepository.delete(liabilityType);
    }
}
