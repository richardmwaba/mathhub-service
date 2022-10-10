package com.hubformath.mathhubservice.ops.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hubformath.mathhubservice.ops.config.models.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    
}