package com.hubformath.mathhubservice.controller.auth;

import com.hubformath.mathhubservice.model.auth.AuthenticatedUser;
import com.hubformath.mathhubservice.model.auth.Login;
import com.hubformath.mathhubservice.model.auth.RefreshToken;
import com.hubformath.mathhubservice.model.auth.Signup;
import com.hubformath.mathhubservice.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody Signup signUpRequest) {
        try {
            return ResponseEntity.ok(authService.registerUser(signUpRequest));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshToken> refreshToken(@Valid @RequestBody RefreshToken requestRefreshToken) {
        try {
            return ResponseEntity.ok(authService.refreshToken(requestRefreshToken));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}