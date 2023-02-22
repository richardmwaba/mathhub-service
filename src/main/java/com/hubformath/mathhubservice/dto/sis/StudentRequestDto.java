package com.hubformath.mathhubservice.dto.sis;

import java.time.LocalDate;
import java.util.List;

import com.hubformath.mathhubservice.model.sis.Address;
import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.model.sis.PhoneNumber;

public class StudentRequestDto {

    private long examBoardId;

    private long gradeId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private Parent parent;

    private List<Address> addresses;

    private List<PhoneNumber> phoneNumber;

    public long getGradeId() {
        return gradeId;}

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;}

    public String getFirstName() {
        return firstName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;}

    public String getMiddleName() {
        return middleName;}

    public void setMiddleName(String middleName) {
        this.middleName = middleName;}

    public String getLastName() {
        return lastName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;}

    public String getEmail() {
        return email;}

    public void setEmail(String email) {
        this.email = email;}

    public LocalDate getDateOfBirth() {
        return dateOfBirth;}

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;}

    public long getExamBoardId() {
        return examBoardId;}

    public void setExamBoardId(long examBoardId) {
        this.examBoardId = examBoardId;}

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

    public List<PhoneNumber> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<PhoneNumber> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
