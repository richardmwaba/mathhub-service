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
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType type;

    @Column(name = "address_line1")
    private String firstAddress;

    @Column(name = "address_line2")
    private String secondAddress;

    @Column(name = "address_line3")
    private String thirdAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "zip_code")
    private String zipCode;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Address() {
    }

    public Address(AddressType type,
                   String firstAddress,
                   String secondAddress,
                   String thirdAddress,
                   String city,
                   String province,
                   String zipCode) {
        this.type = type;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.thirdAddress = thirdAddress;
        this.city = city;
        this.province = province;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    public String getThirdAddress() {
        return thirdAddress;
    }

    public void setThirdAddress(String thirdAddress) {
        this.thirdAddress = thirdAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
        if (!(o instanceof Address address)) return false;
        return Objects.equals(getId(), address.getId())
                && getType() == address.getType()
                && Objects.equals(getFirstAddress(), address.getFirstAddress())
                && Objects.equals(getSecondAddress(), address.getSecondAddress())
                && Objects.equals(getThirdAddress(), address.getThirdAddress())
                && Objects.equals(getCity(), address.getCity())
                && Objects.equals(getProvince(), address.getProvince())
                && Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                            getType(),
                            getFirstAddress(),
                            getSecondAddress(),
                            getThirdAddress(),
                            getCity(),
                            getProvince(),
                            getZipCode());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", firstAddress='" + firstAddress + '\'' +
                ", secondAddress='" + secondAddress + '\'' +
                ", thirdAddress='" + thirdAddress + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
