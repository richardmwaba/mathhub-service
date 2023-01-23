package com.hubformath.mathhubservice.models.sis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.hubformath.mathhubservice.models.config.Grade;
import com.hubformath.mathhubservice.models.config.ExamBoard;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sxamBoard_id")
    private ExamBoard examBoard;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Student() {
    }

    public Student(String firstName, String middleName, String lastName, String email, LocalDate dateOfBirth) {
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

   public Grade getGrade() { return grade; }

   public void setGrade(Grade grade) { this.grade = grade; }

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ExamBoard getExamBoard() {return examBoard;}

    public void setExamBoard(ExamBoard sxamBoard) {this.examBoard = sxamBoard;}

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
                && Objects.equals(this.grade, student.grade)
                && Objects.equals(this.parent, student.parent)
                && Objects.equals(this.addresses, student.addresses)
                && Objects.equals(this.phoneNumbers, student.phoneNumbers)
                && Objects.equals(this.email, student.email)
                && Objects.equals(this.dateOfBirth, student.dateOfBirth)
                && Objects.equals(this.examBoard, student.examBoard)
                && Objects.equals(this.createdAt, student.createdAt)
                && Objects.equals(this.updatedAt, student.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.middleName, this.lastName, this.addresses, this.grade,
                this.parent, this.phoneNumbers, this.email, this.dateOfBirth, this.examBoard, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Student {id=" + this.id + ", firstName=" + this.firstName + ", middleName=" + this.middleName
                + ", LastName="
                + this.lastName + "grade=" + this.grade + ", parents=" + this.parent + ", addresses=" + this.addresses + ", phoneNumbers="
                + this.phoneNumbers + ", email=" + this.email + ", dateOfBirth=" + this.dateOfBirth
                + ", examBoard=" + this.examBoard + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }

}
