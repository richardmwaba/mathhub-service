package com.hubformath.mathhubservice.model.auth;

import jakarta.validation.constraints.NotBlank;

public record Login (@NotBlank String username, @NotBlank String password) {
}
