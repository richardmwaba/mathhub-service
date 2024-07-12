package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.repository.systemconfig.LiabilityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiabilityTypeService {
    private final LiabilityTypeRepository liabilityTypeRepository;

    public LiabilityTypeService(LiabilityTypeRepository liabilityTypeRepository) {
        this.liabilityTypeRepository = liabilityTypeRepository;
    }

    public List<LiabilityType> getAllLiabilityTypes() {
        return liabilityTypeRepository.findAll();
    }

    public LiabilityType getLiabilityTypeById(String liabilityTypeId) {
        return liabilityTypeRepository.findById(liabilityTypeId).orElseThrow();
    }

    public LiabilityType createLiabilityType(LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.save(liabilityTypeRequest);
    }

    public LiabilityType updateLiabilityType(String liabilityTypeId, LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.findById(liabilityTypeId)
                                      .map(liabilityType -> {
                                          Optional.ofNullable(liabilityTypeRequest.getName())
                                                  .ifPresent(liabilityType::setName);
                                          Optional.ofNullable(liabilityTypeRequest.getDescription())
                                                  .ifPresent(liabilityType::setDescription);
                                          return liabilityTypeRepository.save(liabilityType);
                                      })
                                      .orElseThrow();
    }

    public void deleteLiabilityType(String liabilityTypeId) {
        LiabilityType liabilityType = liabilityTypeRepository.findById(liabilityTypeId)
                                                             .orElseThrow();

        liabilityTypeRepository.delete(liabilityType);
    }
}
