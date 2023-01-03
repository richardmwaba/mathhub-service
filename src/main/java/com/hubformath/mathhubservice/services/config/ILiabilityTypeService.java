package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.LiabilityType;

public interface ILiabilityTypeService {
    List<LiabilityType> getAllLiabilityTypes();

    LiabilityType createLiabilityType(LiabilityType liabilityType);

    LiabilityType getLiabilityTypeById(Long id);

    LiabilityType updateLiabilityType(Long id, LiabilityType liabilityType);

    void deleteLiabilityType(Long id);
}
