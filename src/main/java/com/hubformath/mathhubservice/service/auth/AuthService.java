package com.hubformath.mathhubservice.service.auth;

import com.hubformath.mathhubservice.jwt_auth.JwtUtils;
import com.hubformath.mathhubservice.model.auth.AuthRefreshToken;
import com.hubformath.mathhubservice.model.auth.AuthenticatedUser;
import com.hubformath.mathhubservice.model.auth.Login;
import com.hubformath.mathhubservice.model.auth.RefreshToken;
import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.UserAuthDetails;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final AuthRefreshTokenService authRefreshTokenService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       AuthRefreshTokenService authRefreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authRefreshTokenService = authRefreshTokenService;
    }

    public AuthenticatedUser authenticateUser(Login loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtAccessToken(authentication);

        UserAuthDetails userDetails = (UserAuthDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .map(Role::valueOf)
                                       .map(Role::getDescription)
                                       .collect(Collectors.toSet());

        AuthRefreshToken authRefreshToken = authRefreshTokenService.createRefreshToken(userDetails.getUserId());

        return new AuthenticatedUser(jwt,
                                     authRefreshToken.getToken(),
                                     userDetails.getUserId(),
                                     userDetails.getUsername(),
                                     userDetails.getEmail(),
                                     roles);
    }

    public boolean logoutUser(String username) {
        return authRefreshTokenService.deleteRefreshToken(username);
    }

    public RefreshToken refreshToken(RefreshToken requestRefreshToken) throws AuthException {
        try {
            return authRefreshTokenService.refreshToken(requestRefreshToken.refreshToken());
        } catch (NoSuchElementException e) {
            throw new AuthException("Invalid refresh token. Please make a new sign in request.");
        }
    }

}
