package com.hubformath.mathhubservice.models.sis;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parent {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;

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

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Parent))
            return false;
        Parent student = (Parent) o;
        return Objects.equals(this.id, student.id) && Objects.equals(this.firstName, student.firstName)
            && Objects.equals(this.middleName, student.middleName)
            && Objects.equals(this.lastName, student.lastName)
            && Objects.equals(this.email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.middleName, this.lastName, this.email);
    }

    @Override
    public String toString() {
        return "Parent {id=" + this.id + ", firstName=" + this.firstName + ", middleName=" + this.middleName + ", LastName="
                + this.lastName + ", email=" + this.email + "}";
    }
}
