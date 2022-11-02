package com.hubformath.mathhubservice.models.sis;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Student {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private Parent parent;

    private String guardian;

    private String email;
    
    @OneToMany
    @JoinColumn(name = "address_id")
    private List<Address> addresses;
    
    @OneToMany
    @JoinColumn(name = "phone_number_id")
    private List<PhoneNumber> phoneNumbers;

    private Date dateOfBirth;
    
    public Student() {
    }

    public Student(String firstName, String middleName, String lastName, String email, Date dateOfBirth) {
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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
            && Objects.equals(this.parent, student.parent)
            && Objects.equals(this.addresses, student.addresses)
            && Objects.equals(this.phoneNumbers, student.phoneNumbers)
            && Objects.equals(this.email, student.email)
            && Objects.equals(this.dateOfBirth, student.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.middleName, this.lastName,
                            this.parent, this.guardian, this.email, this.dateOfBirth);
    }

    @Override
    public String toString() {
        return "Student {id=" + this.id + ", firstName=" + this.firstName + ", middleName=" + this.middleName + ", LastName="
                + this.lastName + ", parent=" + this.parent + ", addresses=" + this.addresses + ", phoneNumbers=" 
                + this.phoneNumbers + ", email=" + this.email + ", dateOfBirth=" + this.dateOfBirth + "}";
    } 
}
