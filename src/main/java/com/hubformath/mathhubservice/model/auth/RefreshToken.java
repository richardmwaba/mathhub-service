package com.hubformath.mathhubservice.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshToken(@JsonProperty String accessToken,
                           @NotBlank String refreshToken,
                           @JsonProperty String tokenType ) {

    public RefreshToken(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}
