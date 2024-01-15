package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.repository.auth.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    private final UserRepository usersRepository;

    public UsersService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return usersRepository.findById(userId).orElseThrow();
    }

    public User updateUser(UUID userId, User userRequest) {
        return usersRepository.findById(userId)
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
                                  return usersRepository.save(user);
                              })
                              .orElseThrow();
    }

    public void deleteUser(UUID userId) {
        User user = usersRepository.findById(userId)
                                   .orElseThrow();

        usersRepository.delete(user);
    }

}
