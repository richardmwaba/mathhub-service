package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.models.config.SessionType;


public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    
}
