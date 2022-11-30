package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.LiabilityType;

public interface ILiabilityTypeService {
    public List<LiabilityType> getAllLiabilityTypes();

    public LiabilityType createLiabilityType(LiabilityType liabilityType);

    public LiabilityType getLiabilityTypeById(Long id);

    public LiabilityType updateLiabilityType(Long id, LiabilityType liabilityType);

    public void deleteLiabilityType(Long id);
}
