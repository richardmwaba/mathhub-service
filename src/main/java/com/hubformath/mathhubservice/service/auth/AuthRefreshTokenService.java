package com.hubformath.mathhubservice.service.auth;

import com.hubformath.mathhubservice.jwt_auth.JwtUtils;
import com.hubformath.mathhubservice.model.auth.AuthRefreshToken;
import com.hubformath.mathhubservice.model.auth.RefreshToken;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.repository.auth.AuthRefreshTokenRepository;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import jakarta.security.auth.message.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.qoomon.UncheckedExceptions.unchecked;

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

    public AuthRefreshToken createRefreshToken(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        authRefreshTokenRepository.findByUser(user).ifPresent(authRefreshTokenRepository::delete);

        return createNewRefreshToken(userId);
    }

    public boolean deleteRefreshToken(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return authRefreshTokenRepository.findByUser(user)
                                         .map(authRefreshToken -> {
                                             authRefreshTokenRepository.delete(authRefreshToken);
                                             return true;
                                         }).orElse(false);
    }

    public RefreshToken refreshToken(String refreshToken) {
        return authRefreshTokenRepository.findByToken(refreshToken)
                                         .map(authRefreshToken -> unchecked(() -> verifyExpiration(authRefreshToken)))
                                         .map(AuthRefreshToken::getUser)
                                         .map(user -> {
                                             String authToken = jwtUtils.generateJwtAccessToken(user.getUsername());
                                             Set<String> roles = user.getUserRoles().stream()
                                                                     .map(userRole -> userRole.getRole().getDescription())
                                                                     .collect(Collectors.toSet());
                                             return new RefreshToken(authToken, refreshToken, user.getUsername(), roles);
                                         }).orElseThrow();
    }

    private AuthRefreshToken createNewRefreshToken(String userId) {
        AuthRefreshToken authRefreshToken = new AuthRefreshToken();

        authRefreshToken.setUser(userRepository.findById(userId).orElseThrow());
        authRefreshToken.setExpiryDateTime(Instant.now().plusMillis(refreshTokenDurationMs));
        authRefreshToken.setToken(UUID.randomUUID().toString());

        return authRefreshTokenRepository.save(authRefreshToken);
    }

    private AuthRefreshToken verifyExpiration(AuthRefreshToken authRefreshToken) throws AuthException {
        if (authRefreshToken.getExpiryDateTime().compareTo(Instant.now()) < 0) {
            authRefreshTokenRepository.delete(authRefreshToken);
            LOGGER.info("Refresh token {} is expired and has been deleted.", authRefreshToken.getToken());
            throw new AuthException("Refresh token is expired. Please make a new sign in request.");
        }

        return authRefreshToken;
    }
}
