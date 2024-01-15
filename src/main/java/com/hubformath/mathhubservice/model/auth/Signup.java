package com.hubformath.mathhubservice.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record Signup(@NotBlank @Size(min = 3) String username,
                     @NotBlank @Size(min = 2) String firstName,
                     @NotBlank @Size(min = 2) String lastName,
                     @NotBlank @Size(min = 8) String password,
                     @NotBlank @Size(max = 50) @Email String email,
                     Set<String> roles) {
}
