package com.hubformath.mathhubservice.repository.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {

}
