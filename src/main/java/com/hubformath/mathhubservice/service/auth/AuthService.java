package com.hubformath.mathhubservice.service.auth;

import com.hubformath.mathhubservice.jwt_auth.JwtUtils;
import com.hubformath.mathhubservice.model.auth.AuthRefreshToken;
import com.hubformath.mathhubservice.model.auth.AuthenticatedUser;
import com.hubformath.mathhubservice.model.auth.Login;
import com.hubformath.mathhubservice.model.auth.RefreshToken;
import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.Signup;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserAuthDetails;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    public static final String ROLE_IS_NOT_FOUND = "Role is not found.";

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final AuthRefreshTokenService authRefreshTokenService;

    public AuthService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder,
                       AuthRefreshTokenService authRefreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
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
                                       .collect(Collectors.toSet());

        AuthRefreshToken authRefreshToken = authRefreshTokenService.createRefreshToken(userDetails.getUserId());

        return new AuthenticatedUser(jwt,
                                     authRefreshToken.getToken(),
                                     userDetails.getUserId(),
                                     userDetails.getUsername(),
                                     userDetails.getEmail(),
                                     roles);
    }

    public boolean logoutUser(String refreshToken) {
        Authentication authentication = new AnonymousAuthenticationToken("anonymousUser",
                                                                         "anonymousUser",
                                                                         Set.of(new SimpleGrantedAuthority(Role.ROLE_ANONYMOUS.name())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authRefreshTokenService.deleteRefreshToken(refreshToken);
    }

    public String registerUser(Signup signUpRequest) throws AuthException {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            throw new AuthException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new AuthException("Email is already in use!");
        }

        User user = new User(signUpRequest.username(),
                             signUpRequest.email(),
                             passwordEncoder.encode(signUpRequest.password()));

        Set<String> requestRoles = signUpRequest.roles();
        Set<UserRole> userRoles = new HashSet<>();

        if (requestRoles == null) {
            UserRole guestRole = userRoleRepository.findByRoleName(Role.ROLE_GUEST)
                                                   .orElseThrow(() -> new RuntimeException(ROLE_IS_NOT_FOUND));
            userRoles.add(guestRole);
        } else {
            requestRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        UserRole adminRole = userRoleRepository.findByRoleName(Role.ROLE_ADMIN)
                                                               .orElseThrow(() -> new RuntimeException(ROLE_IS_NOT_FOUND));
                        userRoles.add(adminRole);
                    }
                    case "student" -> {
                        UserRole studentRole = userRoleRepository.findByRoleName(Role.ROLE_STUDENT)
                                                                 .orElseThrow(() -> new RuntimeException(
                                                                         ROLE_IS_NOT_FOUND));
                        userRoles.add(studentRole);
                    }
                    case "teacher" -> {
                        UserRole teacherRole = userRoleRepository.findByRoleName(Role.ROLE_TEACHER)
                                                                 .orElseThrow(() -> new RuntimeException(
                                                                         ROLE_IS_NOT_FOUND));
                        userRoles.add(teacherRole);
                    }
                    case "parent" -> {
                        UserRole parentRole = userRoleRepository.findByRoleName(Role.ROLE_PARENT)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                        ROLE_IS_NOT_FOUND));
                        userRoles.add(parentRole);
                    }
                    case "cashier" -> {
                        UserRole cashierRole = userRoleRepository.findByRoleName(Role.ROLE_CASHIER)
                                                                 .orElseThrow(() -> new RuntimeException(
                                                                         ROLE_IS_NOT_FOUND));
                        userRoles.add(cashierRole);
                    }
                    default -> {
                        UserRole guestRole = userRoleRepository.findByRoleName(Role.ROLE_GUEST)
                                                               .orElseThrow(() -> new RuntimeException(ROLE_IS_NOT_FOUND));
                        userRoles.add(guestRole);
                    }
                }
            });
        }

        user.setUserRoles(userRoles);
        userRepository.save(user);

        return "User registered successfully!";
    }

    public RefreshToken refreshToken(RefreshToken requestRefreshToken) throws AuthException {
        try {
            return authRefreshTokenService.refreshToken(requestRefreshToken.refreshToken());
        } catch (NoSuchElementException e) {
            throw new AuthException("Invalid refresh token. Please make a new sign in request.");
        }
    }

}
