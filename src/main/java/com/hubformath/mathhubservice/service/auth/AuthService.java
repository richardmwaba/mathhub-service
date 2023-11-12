package com.hubformath.mathhubservice.service.auth;

import com.hubformath.mathhubservice.jwt_auth.JwtUtils;
import com.hubformath.mathhubservice.model.auth.AuthenticatedUser;
import com.hubformath.mathhubservice.model.auth.Login;
import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.Signup;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserAuthDetails;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashSet;
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

    public AuthService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticatedUser authenticateUser(Login loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserAuthDetails userDetails = (UserAuthDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .collect(Collectors.toSet());

        return new AuthenticatedUser(jwt,
                                     userDetails.getUserId(),
                                     userDetails.getUsername(),
                                     userDetails.getEmail(),
                                     roles);
    }

    public String registerUser(Signup signUpRequest) throws AuthenticationException {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            throw new AuthenticationException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new AuthenticationException("Email is already in use!");
        }

        User user = new User(signUpRequest.username(),
                             signUpRequest.email(),
                             passwordEncoder.encode(signUpRequest.password()));

        Set<String> requestRoles = signUpRequest.roles();
        Set<UserRole> userRoles = new HashSet<>();

        if (requestRoles == null) {
            UserRole guestRole = userRoleRepository.findByRoleName(Role.ROLE_GUEST)
                                                   .orElseThrow(() -> new AuthenticationException(ROLE_IS_NOT_FOUND));
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
}
