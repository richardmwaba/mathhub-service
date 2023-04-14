package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;

import java.util.UUID;


public interface SessionTypeRepository extends JpaRepository<SessionType, UUID> {
    
}
