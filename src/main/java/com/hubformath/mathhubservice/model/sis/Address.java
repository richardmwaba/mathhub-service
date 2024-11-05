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
    @JsonIgnore
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType type;

    @Column(name = "address_line1")
    private String firstAddressLine;

    @Column(name = "address_line2")
    private String secondAddressLine;

    @Column(name = "address_line3")
    private String thirdAddressLine;

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

    @SuppressWarnings("unused")
    public Address() {
        // Default constructor required by Hibernate
    }

    public Address(AddressType type,
                   String firstAddressLine,
                   String secondAddressLine,
                   String thirdAddressLine,
                   String city,
                   String province,
                   String zipCode) {
        this.type = type;
        this.firstAddressLine = firstAddressLine;
        this.secondAddressLine = secondAddressLine;
        this.thirdAddressLine = thirdAddressLine;
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

    public String getFirstAddressLine() {
        return firstAddressLine;
    }

    public void setFirstAddressLine(String firstAddressLine) {
        this.firstAddressLine = firstAddressLine;
    }

    public String getSecondAddressLine() {
        return secondAddressLine;
    }

    public void setSecondAddressLine(String secondAddressLine) {
        this.secondAddressLine = secondAddressLine;
    }

    public String getThirdAddressLine() {
        return thirdAddressLine;
    }

    public void setThirdAddressLine(String thirdAddressLine) {
        this.thirdAddressLine = thirdAddressLine;
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
                && Objects.equals(getFirstAddressLine(), address.getFirstAddressLine())
                && Objects.equals(getSecondAddressLine(), address.getSecondAddressLine())
                && Objects.equals(getThirdAddressLine(), address.getThirdAddressLine())
                && Objects.equals(getCity(), address.getCity())
                && Objects.equals(getProvince(), address.getProvince())
                && Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                            getType(),
                            getFirstAddressLine(),
                            getSecondAddressLine(),
                            getThirdAddressLine(),
                            getCity(),
                            getProvince(),
                            getZipCode());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", firstAddress='" + firstAddressLine + '\'' +
                ", secondAddress='" + secondAddressLine + '\'' +
                ", thirdAddress='" + thirdAddressLine + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
