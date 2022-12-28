package com.hubformath.mathhubservice.models.sis;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private AddressType addressType;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String province;

    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Address() {
    }

    public Address(AddressType addressType, String addressLine1, String addressLine2, String addressLine3, String city,
            String province, String zipCode) {
        this.addressType = addressType;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.city = city;
        this.province = province;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddresLine1() {
        return addressLine1;
    }

    public void setAddresLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
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
        if (!(o instanceof Address))
            return false;
        Address address = (Address) o;
        return Objects.equals(this.id, address.id) && Objects.equals(this.addressType, address.addressType)
            && Objects.equals(this.addressLine1, address.addressLine1)
            && Objects.equals(this.addressLine2, address.addressLine2)
            && Objects.equals(this.addressLine3, address.addressLine3)
            && Objects.equals(this.city, address.city)
            && Objects.equals(this.province, address.province)
            && Objects.equals(this.zipCode, address.zipCode)
            && Objects.equals(this.student, address.student)
            && Objects.equals(this.parent, address.parent)
            && Objects.equals(this.createdAt, address.createdAt)
            && Objects.equals(this.updatedAt, address.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.addressType, this.addressLine1, this.addressLine2, this.addressLine3,
                            this.city, this.province, this.zipCode, this.student, this.parent);
    }

    @Override
    public String toString() {
        return "Address{id=" + this.id + ", addressType=" + this.addressType + ", addressLine1=" + this.addressLine1 
                + ", addressLine2=" + this.addressLine2 + ", addressLine3=" + this.addressLine3 
                + ", city=" + this.city + ", province=" + this.province + ", zipCode=" + this.zipCode 
                + ", student=" + this.student + ", parent=" + this.parent + ", createdAt=" + this.createdAt 
                + ", updatedAt=" + this.updatedAt +"}";
    }
}
