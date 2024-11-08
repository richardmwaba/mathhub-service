package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.auth.UserRequest;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody UserRequest userRequest) {
        try {
            User newUser = usersService.createUser(userRequest);
            EntityModel<User> userEntityModel = toModel(newUser);

            return ResponseEntity.created(userEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                 .body(userEntityModel);
        } catch (AuthException | NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
    public ResponseEntity<CollectionModel<?>> getAllUsers() {
        List<User> users = usersService.getAllUsers();
        return ResponseEntity.ok().body(toCollectionModel(users));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable String userId) {
        try {
            User user = usersService.getUserById(userId);
            EntityModel<User> userEntityModel = toModel(user);

            return ResponseEntity.ok().body(userEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable String userId,
                                                        @RequestBody UserRequest userRequest) {
        try {
            User updatedUser = usersService.updateUser(userId, userRequest);
            EntityModel<User> userEntityModel = toModel(updatedUser);

            return ResponseEntity.ok().body(userEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            usersService.deleteUser(userId);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                                                                        .getUserById(user.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                                                                        .getAllUsers()).withRel("users"));
    }

    private CollectionModel<?> toCollectionModel(List<User> users) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                                                              .getAllUsers()).withSelfRel();
        List<EntityModel<User>> usersEntities = users.stream()
                                                     .map(this::toModel)
                                                     .toList();

        return users.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(User.class, link)
                : CollectionModel.of(usersEntities, link);
    }
}
