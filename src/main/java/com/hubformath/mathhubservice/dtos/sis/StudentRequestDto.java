package com.hubformath.mathhubservice.dtos.sis;

import java.time.LocalDate;

public class StudentRequestDto {

    private long gradeId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    public long getGradeId() {return gradeId;}

    public void setGradeId(long gradeId) {this.gradeId = gradeId;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getMiddleName() {return middleName;}

    public void setMiddleName(String middleName) {this.middleName = middleName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public LocalDate getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth;}

}
