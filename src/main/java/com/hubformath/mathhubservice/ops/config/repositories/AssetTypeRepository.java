package com.hubformath.mathhubservice.ops.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.ops.config.models.AssetType;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    
}
