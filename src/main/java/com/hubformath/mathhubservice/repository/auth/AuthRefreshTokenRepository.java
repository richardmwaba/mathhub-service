package com.hubformath.mathhubservice.repository.auth;

import com.hubformath.mathhubservice.model.auth.AuthRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRefreshTokenRepository extends JpaRepository<AuthRefreshToken, UUID> {

    Optional<AuthRefreshToken> findByToken(String token);

}
