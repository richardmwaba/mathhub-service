package com.hubformath.mathhubservice.sis.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.sis.config.models.SessionType;

public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    
}
