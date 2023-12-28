package com.hubformath.mathhubservice.service.auth;

import com.hubformath.mathhubservice.jwt_auth.JwtUtils;
import com.hubformath.mathhubservice.model.auth.AuthRefreshToken;
import com.hubformath.mathhubservice.model.auth.RefreshToken;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.repository.auth.AuthRefreshTokenRepository;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthRefreshTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRefreshTokenService.class);

    @Value("${mathhub.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final AuthRefreshTokenRepository authRefreshTokenRepository;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthRefreshTokenService(AuthRefreshTokenRepository authRefreshTokenRepository,
                                   UserRepository userRepository,
                                   JwtUtils jwtUtils) {
        this.authRefreshTokenRepository = authRefreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public AuthRefreshToken createRefreshToken(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return authRefreshTokenRepository.findByUser(user)
                                         .map(authRefreshToken -> {
                                             authRefreshTokenRepository.delete(authRefreshToken);
                                             return createNewRefreshToken(userId);
                                         }).orElseGet(() -> createNewRefreshToken(userId));
    }

    private AuthRefreshToken createNewRefreshToken(UUID userId) {
        AuthRefreshToken authRefreshToken = new AuthRefreshToken();

        authRefreshToken.setUser(userRepository.findById(userId).orElseThrow());
        authRefreshToken.setExpiryDateTime(Instant.now().plusMillis(refreshTokenDurationMs));
        authRefreshToken.setToken(UUID.randomUUID().toString());

        return authRefreshTokenRepository.save(authRefreshToken);
    }

    public RefreshToken refreshToken(String refreshToken) {
        return authRefreshTokenRepository.findByToken(refreshToken)
                                  .map(this::verifyExpiration)
                                  .map(AuthRefreshToken::getUser)
                                  .map(user -> {
                                      String authToken = jwtUtils.generateJwtToken(user.getUsername());
                                      return new RefreshToken(authToken, refreshToken);
                                  }).orElseThrow();
    }

    private AuthRefreshToken verifyExpiration(AuthRefreshToken authRefreshToken) {
        if (authRefreshToken.getExpiryDateTime().compareTo(Instant.now()) < 0) {
            authRefreshTokenRepository.delete(authRefreshToken);
            LOGGER.info("Refresh token {} is expired and has been deleted.", authRefreshToken.getToken());
        }

        return authRefreshToken;
    }
}
