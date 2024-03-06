package com.hubformath.mathhubservice.repository.auth;

import com.hubformath.mathhubservice.model.auth.AuthRefreshToken;
import com.hubformath.mathhubservice.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRefreshTokenRepository extends JpaRepository<AuthRefreshToken, String> {

    Optional<AuthRefreshToken> findByToken(String token);

    Optional<AuthRefreshToken> findByUser(User user);

}
