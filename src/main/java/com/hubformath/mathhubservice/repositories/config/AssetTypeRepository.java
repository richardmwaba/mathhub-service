package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.AssetType;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    
}
