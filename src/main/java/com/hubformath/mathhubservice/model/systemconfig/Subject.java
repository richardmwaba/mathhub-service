package com.hubformath.mathhubservice.model.systemconfig;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String subjectName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade subjectGrade;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Subject() {
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
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
                && Objects.equals(this.createdAt, subject.createdAt)
                && Objects.equals(this.updatedAt, subject.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.subjectName, this.subjectGrade, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Subject {id=" + this.id + ", subjectName=" + this.subjectName + ", " +
                "subjectGrade=" + this.subjectGrade + ", createdAt=" + this.createdAt +
                ", updatedAt=" + this.updatedAt + "}";
    }
}
