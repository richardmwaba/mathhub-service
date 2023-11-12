package com.hubformath.mathhubservice.model.sis;

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
import java.util.UUID;

@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "phone_number_id")
    private UUID phoneNumberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_number_type")
    private PhoneNumberType type;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "number")
    private String number;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PhoneNumber() {
    }

    public PhoneNumber(PhoneNumberType type, String countryCode, String number) {
        this.type = type;
        this.countryCode = countryCode;
        this.number = number;
    }

    public UUID getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(UUID phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
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
        return Objects.equals(getPhoneNumberId(), that.getPhoneNumberId())
                && getType() == that.getType()
                && Objects.equals(getCountryCode(), that.getCountryCode())
                && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumberId(), getType(), getCountryCode(), getNumber());
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phoneNumberId=" + phoneNumberId +
                ", type=" + type +
                ", countryCode='" + countryCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
