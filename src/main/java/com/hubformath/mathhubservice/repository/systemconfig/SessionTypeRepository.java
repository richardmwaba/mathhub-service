package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;


public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    
}
