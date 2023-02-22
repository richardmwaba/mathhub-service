package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;

public interface ILiabilityTypeService {
    List<LiabilityType> getAllLiabilityTypes();

    LiabilityType createLiabilityType(LiabilityType liabilityType);

    LiabilityType getLiabilityTypeById(Long id);

    LiabilityType updateLiabilityType(Long id, LiabilityType liabilityType);

    void deleteLiabilityType(Long id);
}
