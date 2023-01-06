package com.hubformath.mathhubservice.models.sis;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PhoneNumberType type;

    private String countryCode;

    private String number;

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
    
    public PhoneNumber() {
    }

    public PhoneNumber(PhoneNumberType type, String countryCode, String number) {
        this.type = type;
        this.countryCode = countryCode;
        this.number = number;
    }

    public Long getId() {
        return id;
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
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber phoneNumber = (PhoneNumber) o;
        return Objects.equals(this.id, phoneNumber.id) && Objects.equals(this.type, phoneNumber.type)
            && Objects.equals(this.countryCode, phoneNumber.countryCode)
            && Objects.equals(this.number, phoneNumber.number)
            && Objects.equals(this.student, phoneNumber.student)
            && Objects.equals(this.parent, phoneNumber.parent)
            && Objects.equals(this.createdAt, phoneNumber.createdAt)
            && Objects.equals(this.updatedAt, phoneNumber.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.countryCode, this.number, this.student, this.parent,
                            this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "PhoneNumber {id=" + this.id + ", type=" + this.type + ", countryCode=" + this.countryCode 
                + ", number=" + this.number + ", student=" + this.student + ", parent=" + this.parent 
                + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }

    
}
