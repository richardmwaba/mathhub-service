package com.hubformath.mathhubservice.repository.auth;

import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(Role name);
}