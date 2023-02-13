package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    
}
