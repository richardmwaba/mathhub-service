package com.hubformath.mathhubservice.model.auth;

import java.util.Set;

public record AuthenticatedUser(String accessToken,
                                String refreshToken,
                                String userId,
                                String username,
                                String email,
                                Set<String> roles,
                                String type) {

    public AuthenticatedUser(String accessToken,
                             String refreshToken,
                             String userId,
                             String username,
                             String email,
                             Set<String> roles) {
        this(accessToken, refreshToken, userId, username, email, roles, "Bearer");
    }
}
