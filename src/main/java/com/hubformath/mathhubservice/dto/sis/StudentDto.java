package com.hubformath.mathhubservice.dto.sis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.dto.systemconfig.ExamBoardDto;
import com.hubformath.mathhubservice.dto.systemconfig.GradeDto;
import com.hubformath.mathhubservice.dto.systemconfig.LessonDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentDto {
    private Long id;

    private long examBoardId;

    private long gradeId;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String firstName;

    private String middleName;

    private String lastName;

    private GradeDto grade;

    private List<LessonDto> lessons;

    private String email;

    private ParentDto parent;

    private List<AddressDto> addresses;

    private List<PhoneNumberDto> phoneNumbers;

    private LocalDate dateOfBirth;

    private ExamBoardDto examBoard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public long getExamBoardId() {
        return examBoardId;
    }

    @JsonProperty
    public void setExamBoardId(long examBoardId) {
        this.examBoardId = examBoardId;
    }

    @JsonIgnore
    public long getGradeId() {
        return gradeId;
    }

    @JsonProperty
    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
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

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }

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

    public ExamBoardDto getExamBoard() {return this.examBoard;}

    public void setExamBoard(ExamBoardDto syllabus) {this.examBoard = syllabus;}
}
