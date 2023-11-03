package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionTypeRepository extends JpaRepository<SessionType, UUID> {
    
}
