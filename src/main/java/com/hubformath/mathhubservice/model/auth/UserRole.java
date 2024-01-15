package com.hubformath.mathhubservice.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @ReadOnlyProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id", updatable = false, nullable = false)
    private Long userRoleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private Role roleName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    public UserRole() {
    }

    public UserRole(final Role roleName) {
        this.roleName = roleName;
    }

    public Long getUserRoleId() {
        return this.userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Role getRoleName() {
        return this.roleName;
    }

    public void setRoleName(final Role roleName) {
        this.roleName = roleName;
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
        if (this == o) return true;
        if (!(o instanceof UserRole userRole)) return false;
        return Objects.equals(getUserRoleId(), userRole.getUserRoleId())
                && Objects.equals(getRoleName(), userRole.getRoleName())
                && Objects.equals(getCreatedAt(), userRole.getCreatedAt())
                && Objects.equals(getUpdatedAt(), userRole.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserRoleId(), getRoleName(), getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userRoleId +
                ", userRole=" + roleName +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
