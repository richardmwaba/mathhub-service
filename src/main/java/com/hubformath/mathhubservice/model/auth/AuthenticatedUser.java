package com.hubformath.mathhubservice.model.auth;

import java.util.Set;
import java.util.UUID;

public record AuthenticatedUser(String token,
                                String refreshToken,
                                UUID userId,
                                String username,
                                String email,
                                Set<String> roles,
                                String type) {

    public AuthenticatedUser(String token,
                             String refreshToken,
                             UUID userId,
                             String username,
                             String email,
                             Set<String> roles) {
        this(token, refreshToken, userId, username, email, roles, "Bearer");
    }
}
