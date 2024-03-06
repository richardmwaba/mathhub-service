package com.hubformath.mathhubservice.model.systemconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subject_id", updatable = false, nullable = false)
    private String subjectId;

    @Column(name = "subject_name", nullable = false, unique = true)
    private String subjectName;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_complexity", nullable = false)
    private SubjectComplexity subjectComplexity;

    @ManyToMany
    @JoinTable(name = "subjects_grades",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "grade_id"))
    private Set<Grade> subjectGrades;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Subject() {
    }

    public Subject(final String subjectName, final SubjectComplexity subjectComplexity, final Set<Grade> subjectGrades) {
        this.subjectName = subjectName;
        this.subjectComplexity = subjectComplexity;
        this.subjectGrades = subjectGrades;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectId() {
        return this.subjectId;
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

    public Set<Grade> getSubjectGrades() {
        return subjectGrades;
    }

    public void setSubjectGrades(Set<Grade> grades) {
        this.subjectGrades = grades;
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
        if (!(o instanceof Subject subject)) return false;
        return Objects.equals(getSubjectId(), subject.getSubjectId())
                && Objects.equals(getSubjectName(), subject.getSubjectName())
                && getSubjectComplexity() == subject.getSubjectComplexity()
                && Objects.equals(getSubjectGrades(), subject.getSubjectGrades());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectId(), getSubjectName(), getSubjectComplexity(), getSubjectGrades());
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectComplexity=" + subjectComplexity +
                ", subjectGrades=" + subjectGrades +
                '}';
    }
}
