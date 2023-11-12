package com.hubformath.mathhubservice.model.sis;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @ReadOnlyProperty
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private StudentGender gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "students_lessons",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private List<Lesson> lessons;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "students_addresses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses;

    @ManyToOne
    @JoinColumn(name = "examBoard_id")
    private ExamBoard examBoard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "students_phone_numbers",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_number_id"))
    private List<PhoneNumber> phoneNumbers;

    @Transient
    private StudentFinancialSummary studentFinancialSummary;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @ReadOnlyProperty
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @ReadOnlyProperty
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    public Student() {
        // Used by hibernate instantiation
    }

    public Student(final String firstName,
                   final String middleName,
                   final String lastName,
                   final String email,
                   final LocalDate dateOfBirth,
                   final StudentGender gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
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

    public StudentGender getGender() {
        return gender;
    }

    public void setGender(StudentGender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

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

    public ExamBoard getExamBoard() {
        return examBoard;
    }

    public void setExamBoard(ExamBoard examBoard) {
        this.examBoard = examBoard;
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
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return getStudentId().equals(student.getStudentId())
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
                && getDateOfBirth().equals(student.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(),
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
                            getDateOfBirth());
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
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
                '}';
    }
}
