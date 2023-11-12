package com.hubformath.mathhubservice.model.systemconfig;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "syllabuses")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "syllabus_id", updatable = false, nullable = false)
    private UUID syllabusId;

    @Column(name = "syllabus_name", nullable = false)
    private String syllabusName;

    @Column(name = "syllabus_description", nullable = false)
    private String syllabusDescription;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Syllabus() {
    }

    public Syllabus(String syllabusName, String syllabusDescription) {
        this.syllabusName = syllabusName;
        this.syllabusDescription = syllabusDescription;
    }

    public UUID getSyllabusId() {
        return this.syllabusId;
    }

    public void setSyllabusId(UUID syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getSyllabusName() {
        return this.syllabusName;
    }

    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

    public String getSyllabusDescription() {
        return this.syllabusDescription;
    }

    public void setSyllabusDescription(String syllabusDescription) {
        this.syllabusDescription = syllabusDescription;
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
        if (!(o instanceof Syllabus syllabus)) return false;
        return Objects.equals(getSyllabusId(), syllabus.getSyllabusId())
                && Objects.equals(getSyllabusName(), syllabus.getSyllabusName())
                && Objects.equals(getSyllabusDescription(), syllabus.getSyllabusDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSyllabusId(), getSyllabusName(), getSyllabusDescription());
    }

    @Override
    public String toString() {
        return "Syllabus{" +
                "syllabusId=" + syllabusId +
                ", syllabusName='" + syllabusName + '\'' +
                ", syllabusDescription='" + syllabusDescription + '\'' +
                '}';
    }
}
