package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Parent {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Student> students;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Parent() {
    }

    public Parent(String firstName, String middleName, String lastName, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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
        if (!(o instanceof Parent parent))
            return false;
        return Objects.equals(this.id, parent.id) && Objects.equals(this.firstName, parent.firstName)
            && Objects.equals(this.middleName, parent.middleName)
            && Objects.equals(this.lastName, parent.lastName)
            && Objects.equals(this.email, parent.email)
            && Objects.equals(this.students, parent.students)
            && Objects.equals(this.addresses, parent.addresses)
            && Objects.equals(this.phoneNumbers, parent.phoneNumbers)
            && Objects.equals(this.createdAt, parent.createdAt)
            && Objects.equals(this.updatedAt, parent.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.middleName, this.lastName, this.email, this.students,
                            this.addresses, this.phoneNumbers, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Parent {id=" + this.id + ", firstName=" + this.firstName + ", middleName=" + this.middleName + ", LastName="
                + this.lastName + ", email=" + this.email + ", addresses=" + this.addresses + ", student=" + this.students 
                + ", phoneNumbers=" + this.phoneNumbers + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }
}
