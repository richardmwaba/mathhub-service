package com.hubformath.mathhubservice.repository.systemconfig;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubformath.mathhubservice.model.systemconfig.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    
}