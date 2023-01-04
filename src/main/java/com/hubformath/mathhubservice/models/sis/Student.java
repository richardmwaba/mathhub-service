package com.hubformath.mathhubservice.models.sis;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    @OneToMany(mappedBy = "student")
    private List<Parent> parents;

    private String guardian;

    private String email;
    
    @OneToMany(mappedBy = "student")
    private List<Address> addresses;
    
    @OneToMany(mappedBy = "student")
    private List<PhoneNumber> phoneNumbers;

    private Instant dateOfBirth;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Student() {
    }

    public Student(String firstName, String middleName, String lastName, String email, Instant dateOfBirth) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        if (!(o instanceof Student))
            return false;
        Student student = (Student) o;
        return Objects.equals(this.id, student.id) && Objects.equals(this.firstName, student.firstName)
            && Objects.equals(this.middleName, student.middleName)
            && Objects.equals(this.lastName, student.lastName)
            && Objects.equals(this.parents, student.parents)
            && Objects.equals(this.addresses, student.addresses)
            && Objects.equals(this.phoneNumbers, student.phoneNumbers)
            && Objects.equals(this.email, student.email)
            && Objects.equals(this.dateOfBirth, student.dateOfBirth)
            && Objects.equals(this.createdAt, student.createdAt)
            && Objects.equals(this.updatedAt, student.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.middleName, this.lastName,
                            this.parents, this.guardian, this.email, this.dateOfBirth, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Student {id=" + this.id + ", firstName=" + this.firstName + ", middleName=" + this.middleName + ", LastName="
                + this.lastName + ", parents=" + this.parents + ", addresses=" + this.addresses + ", phoneNumbers=" 
                + this.phoneNumbers + ", email=" + this.email + ", dateOfBirth=" + this.dateOfBirth 
                + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    } 
}
