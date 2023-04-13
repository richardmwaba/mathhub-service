package com.hubformath.mathhubservice.model.sis;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Student {
    @Id
    @ReadOnlyProperty
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private StudentGender gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToOne
    @JoinColumn(name = "examBoard_id")
    private ExamBoard examBoard;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    @Transient
    private StudentFinancialSummary studentFinancialSummary;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    @ReadOnlyProperty
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @ReadOnlyProperty
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    public Student() {
        // Used by hibernate instantiation
    }

    public Student(final String firstName, final String middleName, final String lastName, final String email, final LocalDate dateOfBirth, final StudentGender gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
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

    public StudentGender getGender() {return gender; }

    public void setGender(StudentGender gender) {this.gender = gender; }

    public Grade getGrade() { return grade; }

    public void setGrade(Grade grade) { this.grade = grade; }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
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

    public StudentFinancialSummary getStudentFinancialSummary() {
        return studentFinancialSummary;
    }

    public void setStudentFinancialSummary(StudentFinancialSummary studentFinancialSummary) {
        this.studentFinancialSummary = studentFinancialSummary;
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

    public boolean isOwingPayment() {
        return getLessons().stream().anyMatch(lesson -> lesson.getLessonPaymentStatus() == PaymentStatus.UNPAID);
    }

    public ExamBoard getExamBoard() {return examBoard;}

    public void setExamBoard(ExamBoard examBoard) {this.examBoard = examBoard;}

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
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return getId().equals(student.getId())
                && getFirstName().equals(student.getFirstName())
                && getMiddleName().equals(student.getMiddleName())
                && getLastName().equals(student.getLastName())
                && getGender().equals(student.getGender())
                && getParent().equals(student.getParent())
                && getGrade().equals(student.getGrade())
                && getLessons().equals(student.getLessons())
                && getEmail().equals(student.getEmail())
                && getAddresses().equals(student.getAddresses())
                && getExamBoard().equals(student.getExamBoard())
                && getPhoneNumbers().equals(student.getPhoneNumbers())
                && getStudentFinancialSummary().equals(student.getStudentFinancialSummary())
                && getDateOfBirth().equals(student.getDateOfBirth())
                && getCreatedAt().equals(student.getCreatedAt())
                && getUpdatedAt().equals(student.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getFirstName(),
                getMiddleName(),
                getLastName(),
                getGender(),
                getParent(),
                getGrade(),
                getLessons(),
                getEmail(),
                getAddresses(),
                getExamBoard(),
                getPhoneNumbers(),
                getStudentFinancialSummary(),
                getDateOfBirth(),
                getCreatedAt(),
                getUpdatedAt());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName=" + firstName + '\'' +
                ", middleName=" + middleName + '\'' +
                ", lastName=" + lastName + '\'' +
                ", gender=" + gender +
                ", parent=" + parent +
                ", grade=" + grade +
                ", lessons=" + lessons +
                ", email=" + email + '\'' +
                ", addresses=" + addresses +
                ", examBoard=" + examBoard +
                ", phoneNumbers=" + phoneNumbers +
                ", studentFinancialSummary=" + studentFinancialSummary +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
