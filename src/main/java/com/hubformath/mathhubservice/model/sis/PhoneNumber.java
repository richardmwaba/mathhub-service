package com.hubformath.mathhubservice.model.sis;

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

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PhoneNumberType type;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "number")
    private String number;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    public PhoneNumber() {
        // Default constructor for Hibernate
    }

    public PhoneNumber(PhoneNumberType type, String countryCode, String number) {
        this.type = type;
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PhoneNumberType getType() {
        return type;
    }

    public void setType(PhoneNumberType type) {
        this.type = type;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        if (!(o instanceof PhoneNumber that)) return false;
        return Objects.equals(getId(), that.getId())
                && getType() == that.getType()
                && Objects.equals(getCountryCode(), that.getCountryCode())
                && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getCountryCode(), getNumber());
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", countryCode='" + countryCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
