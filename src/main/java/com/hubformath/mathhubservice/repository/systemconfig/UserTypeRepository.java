package com.hubformath.mathhubservice.repository.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}