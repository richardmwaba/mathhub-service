package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;

import java.util.UUID;

public interface AssetTypeRepository extends JpaRepository<AssetType, UUID> {
    
}
