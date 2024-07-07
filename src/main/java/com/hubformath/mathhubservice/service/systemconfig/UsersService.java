package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserAuthDetails;
import com.hubformath.mathhubservice.model.auth.UserRequest;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.repository.auth.AuthRefreshTokenRepository;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import com.hubformath.mathhubservice.repository.sis.StudentRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    private final AuthRefreshTokenRepository authRefreshTokenRepository;

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository,
                        UserRoleRepository userRoleRepository,
                        AuthRefreshTokenRepository authRefreshTokenRepository,
                        StudentRepository studentRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authRefreshTokenRepository = authRefreshTokenRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(UserRequest userRequest) throws AuthException {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new AuthException("Username is already taken!");
        }

        if (userRepository.existsByEmail(userRequest.email())) {
            throw new AuthException("Email is already in use!");
        }

        Set<UserRole> userRoles = extractUserRoles(userRequest.userRoles());
        User user = new User(userRequest.username(),
                             userRequest.firstName(),
                             userRequest.lastName(),
                             userRequest.gender(),
                             userRequest.email(),
                             passwordEncoder.encode(userRequest.password()));
        user.setUserRoles(userRoles);
        User createdUser = userRepository.save(user);

        if (!createdUser.getUserId().isEmpty() && userRoles.contains(getUserRole(ROLE_STUDENT))) {
            studentRepository.save(new Student(userRequest.firstName(),
                                               userRequest.lastName(),
                                               userRequest.email(),
                                               userRequest.gender(),
                                               createdUser.getUserId()));
        }

        return createdUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User updateUser(String userId, UserRequest userRequest) {
        return userRepository.findById(userId)
                             .map(user -> {
                                 Optional.ofNullable(userRequest.username())
                                         .ifPresent(user::setUsername);
                                 Optional.ofNullable(userRequest.firstName())
                                         .ifPresent(user::setFirstName);
                                 Optional.ofNullable(userRequest.lastName())
                                         .ifPresent(user::setLastName);
                                 Optional.ofNullable(userRequest.middleName())
                                         .ifPresent(user::setMiddleName);
                                 Optional.ofNullable(userRequest.email())
                                         .ifPresent(user::setEmail);
                                 Optional.ofNullable(userRequest.phoneNumber())
                                         .ifPresent(user::setPhoneNumber);
                                 Optional.ofNullable(userRequest.password())
                                         .ifPresent(user::setPassword);
                                 Optional.ofNullable(userRequest.userRoles())
                                         .ifPresent(userRoles -> user.setUserRoles(extractUserRoles(userRoles)));
                                 return userRepository.save(user);
                             })
                             .orElseThrow();
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        authRefreshTokenRepository.findByUser(user).ifPresent(authRefreshTokenRepository::delete);
        userRepository.delete(user);
    }

    private Set<UserRole> extractUserRoles(Set<String> requestRoles) {
        Set<UserRole> userRoles = new HashSet<>();
        if (requestRoles == null) {
            userRoles.add(getUserRole(ROLE_GUEST));
        } else {
            requestRoles.forEach(role -> {
                Role roleEnum = Objects.requireNonNull(Role.fromDescription(role));
                switch (roleEnum) {
                    case ROLE_ADMINISTRATOR -> userRoles.add(getUserRole(ROLE_ADMINISTRATOR));
                    case ROLE_STUDENT -> userRoles.add(getUserRole(ROLE_STUDENT));
                    case ROLE_TEACHER -> userRoles.add(getUserRole(ROLE_TEACHER));
                    case ROLE_PARENT -> userRoles.add(getUserRole(ROLE_PARENT));
                    case ROLE_CASHIER -> userRoles.add(getUserRole(ROLE_CASHIER));
                    default -> userRoles.add(getUserRole(ROLE_GUEST));
                }
            });
        }

        return userRoles;
    }

    public User getLoggedInUser() {
        UserAuthDetails userDetails = (UserAuthDetails) SecurityContextHolder.getContext()
                                                                             .getAuthentication()
                                                                             .getPrincipal();
        return getUserById(userDetails.getUserId());
    }

    private UserRole getUserRole(Role role) {
        return userRoleRepository.findByRole(role)
                                 .orElseThrow(() -> new NoSuchElementException(ROLE_IS_NOT_FOUND));
    }

}
