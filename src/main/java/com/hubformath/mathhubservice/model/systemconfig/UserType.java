package com.hubformath.mathhubservice.model.systemconfig;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UserType() {
    }

    public UserType(final UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return this.id;
    }

    public UserRole getRole() {
        return this.userRole;
    }

    public void setRole(final UserRole role) {
        this.userRole = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserType userType))
            return false;
        return Objects.equals(this.id, userType.id) && Objects.equals(this.userRole, userType.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.userRole, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", userRole=" + userRole +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
