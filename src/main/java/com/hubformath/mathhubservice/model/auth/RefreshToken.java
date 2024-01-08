package com.hubformath.mathhubservice.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RefreshToken(@JsonProperty String accessToken,
                           @NotBlank String refreshToken,
                           @JsonProperty String username,
                           @JsonProperty Set<String> roles,
                           @JsonProperty String tokenType) {

    public RefreshToken(String accessToken, String refreshToken, String username, Set<String> roles) {
        this(accessToken, refreshToken, username, roles, "Bearer");
    }
}
