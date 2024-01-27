package com.hubformath.mathhubservice.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserAuthDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID userId;

    private final String username;

    private final String email;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserAuthDetails(UUID userId,
                           String username,
                           String email,
                           String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserAuthDetails build(User user) {
        List<SimpleGrantedAuthority> authorities = user.getUserRoles()
                                                       .stream()
                                                       .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()
                                                                                                           .name()))
                                                       .toList();

        return new UserAuthDetails(user.getUserId(),
                                   user.getUsername(),
                                   user.getEmail(),
                                   user.getPassword(),
                                   authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthDetails that)) return false;
        return Objects.equals(getUserId(), that.getUserId())
                && Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getAuthorities(), that.getAuthorities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getEmail(), getPassword(), getAuthorities());
    }

}
