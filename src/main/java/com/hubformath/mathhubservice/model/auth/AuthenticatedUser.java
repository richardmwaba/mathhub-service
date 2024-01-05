package com.hubformath.mathhubservice.model.auth;

import java.util.Set;
import java.util.UUID;

public record AuthenticatedUser(String accessToken,
                                String refreshToken,
                                UUID userId,
                                String username,
                                String email,
                                Set<String> roles,
                                String type) {

    public AuthenticatedUser(String accessToken,
                             String refreshToken,
                             UUID userId,
                             String username,
                             String email,
                             Set<String> roles) {
        this(accessToken, refreshToken, userId, username, email, roles, "Bearer");
    }
}
