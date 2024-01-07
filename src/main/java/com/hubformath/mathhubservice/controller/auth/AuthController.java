package com.hubformath.mathhubservice.controller.auth;

import com.hubformath.mathhubservice.model.auth.AuthenticatedUser;
import com.hubformath.mathhubservice.model.auth.Login;
import com.hubformath.mathhubservice.model.auth.RefreshToken;
import com.hubformath.mathhubservice.model.auth.Signup;
import com.hubformath.mathhubservice.service.auth.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUser> authenticateUser(@Valid @RequestBody Login loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@Valid @RequestBody Login loginRequest) {
        try {
            if (!authService.logoutUser(loginRequest.username())) {
                return ResponseEntity.badRequest()
                                     .body("Failed to logout user due to non-existent user or refresh token not found.");
            }
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody Signup signUpRequest) {
        try {
            return ResponseEntity.ok(authService.registerUser(signUpRequest));
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshToken> refreshToken(@Valid @RequestBody RefreshToken requestRefreshToken) {
        try {
            return ResponseEntity.ok(authService.refreshToken(requestRefreshToken));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
