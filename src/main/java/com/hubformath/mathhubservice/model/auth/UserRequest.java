package com.hubformath.mathhubservice.model.auth;

import com.hubformath.mathhubservice.model.sis.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record UserRequest(UUID userId,
                          @NotBlank @Size(min = 3) String username,
                          @NotBlank @Size(min = 2) String firstName,
                          @Size(min = 2) String middleName,
                          @NotBlank @Size(min = 2) String lastName,
                          @NotBlank @Size(min = 8) String password,
                          @NotBlank Gender gender,
                          @NotBlank @Size(max = 50) @Email String email,
                          String phoneNumber,
                          Set<String> roles) {
}
