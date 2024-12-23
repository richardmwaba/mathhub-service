package com.hubformath.mathhubservice.config;

import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.model.sis.Gender;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;
import com.hubformath.mathhubservice.model.sis.PhoneNumberType;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SetupDataLoader.class);

    @Value("${mathhub.app.setupPassword}")
    private String setupPassword;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public SetupDataLoader(UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        Set<Role> currentRoles = userRoleRepository.findAll()
                                                   .stream()
                                                   .map(UserRole::getName)
                                                   .collect(Collectors.toSet());

        if (currentRoles.containsAll(Set.of(Role.values()))) {
            return;
        }

        LOGGER.info("Seeding user roles into the database...");

        createRoleIfNotFound(Role.ROLE_ADMINISTRATOR);
        createRoleIfNotFound(Role.ROLE_CASHIER);
        createRoleIfNotFound(Role.ROLE_GUEST);
        createRoleIfNotFound(Role.ROLE_PARENT);
        createRoleIfNotFound(Role.ROLE_STUDENT);
        createRoleIfNotFound(Role.ROLE_TEACHER);

        Set<UserRole> roles = Set.of(userRoleRepository.findByName(Role.ROLE_ADMINISTRATOR)
                                                       .orElseThrow(() -> new IllegalStateException("Role not found.")));
        User user = new User("setup",
                             "Setup",
                             "Setup",
                             Gender.OTHER,
                             "setup@hubformath.com",
                             new PhoneNumber(PhoneNumberType.MOBILE, "+260", "971123456"),
                             passwordEncoder.encode(setupPassword),
                             roles);
        userRepository.save(user);

        LOGGER.info("User roles seeded into the database successfully.");
    }

    @SuppressWarnings("java:S2201") // Ignore user role returned when we save a new role
    private void createRoleIfNotFound(Role role) {
        userRoleRepository.findByName(role).orElseGet(() -> userRoleRepository.save(new UserRole(role)));
    }
}