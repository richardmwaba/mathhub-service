package com.hubformath.mathhubservice.repositories.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.models.config.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    
}