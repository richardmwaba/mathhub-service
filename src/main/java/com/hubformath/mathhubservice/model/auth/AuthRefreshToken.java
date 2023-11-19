package com.hubformath.mathhubservice.model.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "refresh_tokens")
public class AuthRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "refresh_token_id", updatable = false, nullable = false)
    private UUID refreshTokenId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date_time", nullable = false)
    private Instant expiryDateTime;

    public AuthRefreshToken() {
        // Default constructor for Hibernate
    }

    public UUID getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId(UUID refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(Instant expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthRefreshToken that)) return false;
        return Objects.equals(getRefreshTokenId(), that.getRefreshTokenId())
                && Objects.equals(getUser(), that.getUser())
                && Objects.equals(getToken(), that.getToken())
                && Objects.equals(getExpiryDateTime(), that.getExpiryDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRefreshTokenId(), getUser(), getToken(), getExpiryDateTime());
    }

    @Override
    public String toString() {
        return "AuthRefreshToken{" +
                "refreshTokenId=" + refreshTokenId +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", expiryDateTime=" + expiryDateTime +
                '}';
    }
}
