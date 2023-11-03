package com.hubformath.mathhubservice.model.auth;

import java.util.Set;
import java.util.UUID;

public record AuthenticatedUser(String token,
                                UUID userId,
                                String username,
                                String email,
                                Set<String> roles,
                                String type) {

    public AuthenticatedUser(String token,
                             UUID userId,
                             String username,
                             String email,
                             Set<String> roles) {
        this(token, userId, username, email, roles, "Bearer");
    }
}
