package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserRegistration;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.hubformath.mathhubservice.model.auth.Role.ROLE_ADMINISTRATOR;
import static com.hubformath.mathhubservice.model.auth.Role.ROLE_CASHIER;
import static com.hubformath.mathhubservice.model.auth.Role.ROLE_GUEST;
import static com.hubformath.mathhubservice.model.auth.Role.ROLE_PARENT;
import static com.hubformath.mathhubservice.model.auth.Role.ROLE_STUDENT;
import static com.hubformath.mathhubservice.model.auth.Role.ROLE_TEACHER;

@Service
public class UsersService {

    public static final String ROLE_IS_NOT_FOUND = "Role is not found.";

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userRepository,
                        UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRegistration userRegistration) throws AuthException {
        if (userRepository.existsByUsername(userRegistration.username())) {
            throw new AuthException("Username is already taken!");
        }

        if (userRepository.existsByEmail(userRegistration.email())) {
            throw new AuthException("Email is already in use!");
        }

        User user = new User(userRegistration.username(),
                             userRegistration.firstName(),
                             userRegistration.lastName(),
                             userRegistration.email(),
                             passwordEncoder.encode(userRegistration.password()));

        Set<String> requestRoles = userRegistration.roles();
        Set<UserRole> userRoles = new HashSet<>();

        if (requestRoles == null) {
            UserRole guestRole = userRoleRepository.findByRole(ROLE_GUEST)
                                                   .orElseThrow(() -> new RuntimeException(ROLE_IS_NOT_FOUND));
            userRoles.add(guestRole);
        } else {
            requestRoles.forEach(role -> {
                Role roleEnum = Objects.requireNonNull(Role.fromDescription(role));
                switch (roleEnum) {
                    case ROLE_ADMINISTRATOR -> {
                        UserRole adminRole = userRoleRepository.findByRole(ROLE_ADMINISTRATOR)
                                                               .orElseThrow(() -> new NoSuchElementException(
                                                                       ROLE_IS_NOT_FOUND));
                        userRoles.add(adminRole);
                    }
                    case ROLE_STUDENT -> {
                        UserRole studentRole = userRoleRepository.findByRole(ROLE_STUDENT)
                                                                 .orElseThrow(() -> new NoSuchElementException(
                                                                         ROLE_IS_NOT_FOUND));
                        userRoles.add(studentRole);
                    }
                    case ROLE_TEACHER -> {
                        UserRole teacherRole = userRoleRepository.findByRole(ROLE_TEACHER)
                                                                 .orElseThrow(() -> new NoSuchElementException(
                                                                         ROLE_IS_NOT_FOUND));
                        userRoles.add(teacherRole);
                    }
                    case ROLE_PARENT -> {
                        UserRole parentRole = userRoleRepository.findByRole(ROLE_PARENT)
                                                                .orElseThrow(() -> new NoSuchElementException(
                                                                        ROLE_IS_NOT_FOUND));
                        userRoles.add(parentRole);
                    }
                    case ROLE_CASHIER -> {
                        UserRole cashierRole = userRoleRepository.findByRole(ROLE_CASHIER)
                                                                 .orElseThrow(() -> new NoSuchElementException(
                                                                         ROLE_IS_NOT_FOUND));
                        userRoles.add(cashierRole);
                    }
                    default -> {
                        UserRole guestRole = userRoleRepository.findByRole(ROLE_GUEST)
                                                               .orElseThrow(() -> new NoSuchElementException(
                                                                       ROLE_IS_NOT_FOUND));
                        userRoles.add(guestRole);
                    }
                }
            });
        }

        user.setUserRoles(userRoles);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User updateUser(UUID userId, User userRequest) {
        return userRepository.findById(userId)
                             .map(user -> {
                                 Optional.ofNullable(userRequest.getUsername())
                                         .ifPresent(user::setUsername);
                                 Optional.ofNullable(userRequest.getFirstName())
                                         .ifPresent(user::setFirstName);
                                 Optional.ofNullable(userRequest.getLastName())
                                         .ifPresent(user::setLastName);
                                 Optional.ofNullable(userRequest.getMiddleName())
                                         .ifPresent(user::setMiddleName);
                                 Optional.ofNullable(userRequest.getEmail())
                                         .ifPresent(user::setEmail);
                                 Optional.ofNullable(userRequest.getPhoneNumber())
                                         .ifPresent(user::setPhoneNumber);
                                 Optional.ofNullable(userRequest.getPassword())
                                         .ifPresent(user::setPassword);
                                 return userRepository.save(user);
                             })
                             .orElseThrow();
    }

    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow();

        userRepository.delete(user);
    }

}
