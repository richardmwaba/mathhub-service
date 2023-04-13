package com.hubformath.mathhubservice.model.systemconfig;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String subjectName;

    @Enumerated(EnumType.STRING)
    private SubjectComplexity subjectComplexity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade subjectGrade;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Subject() {
    }

    public Subject(final String subjectName, final SubjectComplexity subjectComplexity) {
        this.subjectName = subjectName;
        this.subjectComplexity = subjectComplexity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectComplexity getSubjectComplexity() {
        return subjectComplexity;
    }

    public void setSubjectComplexity(SubjectComplexity subjectComplexity) {
        this.subjectComplexity = subjectComplexity;
    }

    public Grade getSubjectGrade() {
        return subjectGrade;
    }

    public void setSubjectGrade(Grade grade) {
        this.subjectGrade = grade;
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
        if (!(o instanceof Subject subject))
            return false;
        return Objects.equals(this.id, subject.id) && Objects.equals(this.subjectName, subject.subjectName)
                && Objects.equals(this.subjectGrade, subject.subjectGrade)
                && Objects.equals(this.subjectComplexity, subject.subjectComplexity)
                && Objects.equals(this.createdAt, subject.createdAt)
                && Objects.equals(this.updatedAt, subject.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.subjectName, this.subjectGrade, this.subjectComplexity, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectName='" + subjectName + '\'' +
                ", subjectComplexity=" + subjectComplexity +
                ", subjectGrade=" + subjectGrade +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
