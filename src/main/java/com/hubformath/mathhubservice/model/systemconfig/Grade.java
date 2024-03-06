package com.hubformath.mathhubservice.model.systemconfig;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "grade_id", updatable = false, nullable = false)
    private String gradeId;

    @Column(name = "grade_name", nullable = false)
    private String gradeName;

    @Column(name = "grade_description", nullable = false)
    private String gradeDescription;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Grade() {
    }

    public Grade(String gradeName, String gradeDescription) {
        this.gradeName = gradeName;
        this.gradeDescription = gradeDescription;
    }


    public String getGradeId() {
        return this.gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return this.gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeDescription() {
        return this.gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grade grade)) return false;
        return Objects.equals(getGradeId(), grade.getGradeId())
                && Objects.equals(getGradeName(), grade.getGradeName())
                && Objects.equals(getGradeDescription(), grade.getGradeDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGradeId(), getGradeName(), getGradeDescription());
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", gradeDescription='" + gradeDescription + '\'' +
                '}';
    }
}
