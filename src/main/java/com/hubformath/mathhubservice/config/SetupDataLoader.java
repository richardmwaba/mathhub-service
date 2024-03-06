package com.hubformath.mathhubservice.config;

import com.hubformath.mathhubservice.model.auth.Role;
import com.hubformath.mathhubservice.model.auth.UserRole;
import com.hubformath.mathhubservice.repository.auth.UserRoleRepository;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SetupDataLoader.class);

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public SetupDataLoader(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        LOGGER.info("Seeding user roles into the database...");
        Set<Role> currentRoles = userRoleRepository.findAll()
                                                   .stream()
                                                   .map(UserRole::getRole)
                                                   .collect(Collectors.toSet());

        if (currentRoles.containsAll(Set.of(Role.values()))) {
            LOGGER.info("User roles already seeded into the database, nothing to do.");
            return;
        }

        createRoleIfNotFound(Role.ROLE_ADMINISTRATOR);
        createRoleIfNotFound(Role.ROLE_CASHIER);
        createRoleIfNotFound(Role.ROLE_GUEST);
        createRoleIfNotFound(Role.ROLE_PARENT);
        createRoleIfNotFound(Role.ROLE_STUDENT);
        createRoleIfNotFound(Role.ROLE_TEACHER);

        LOGGER.info("User roles seeded into the database successfully.");
    }

    private void createRoleIfNotFound(Role role) {
        userRoleRepository.findByRole(role).orElseGet(() -> userRoleRepository.save(new UserRole(role)));
    }
}