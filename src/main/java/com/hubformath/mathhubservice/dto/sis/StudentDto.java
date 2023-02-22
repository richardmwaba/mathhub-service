package com.hubformath.mathhubservice.dto.sis;

import com.hubformath.mathhubservice.dto.systemconfig.GradeDto;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentDto {
    private Long id;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String firstName;

    private String middleName;

    private String lastName;

    private GradeDto grade;

    private String email;

    private ParentDto parent;

    private List<AddressDto> addresses;

    private List<PhoneNumberDto> phoneNumbers;

    private LocalDate dateOfBirth;

    private ExamBoard examBoard;

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

    public GradeDto getGrade() { return grade; }

    public void setGrade(GradeDto grade) { this.grade = grade; }

    public ParentDto getParent() {
        return parent;
    }

    public void setParent(ParentDto parent) {
        this.parent = parent;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public List<PhoneNumberDto> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumberDto> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = LocalDate.parse(dateOfBirth, dateFormatter);
    }

    public ExamBoard getExamBoard() {return this.examBoard;}

    public void setExamBoard(ExamBoard syllabus) {this.examBoard = syllabus;}
}
