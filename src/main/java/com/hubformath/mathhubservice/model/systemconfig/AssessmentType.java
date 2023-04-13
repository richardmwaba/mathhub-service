package com.hubformath.mathhubservice.model.systemconfig;

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
@Table(name = "assessment_type")
public class AssessmentType {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID assessmentTypeId;

    private String typeName;
    
    private String typeDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AssessmentType() {}

    public AssessmentType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public UUID getAssessmentTypeId() {
        return this.assessmentTypeId;
    }

    public void setAssessmentTypeId(UUID assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }
    
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return this.typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
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
        if (!(o instanceof AssessmentType that)) return false;
        return Objects.equals(getAssessmentTypeId(), that.getAssessmentTypeId())
                && Objects.equals(getTypeName(), that.getTypeName())
                && Objects.equals(getTypeDescription(), that.getTypeDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssessmentTypeId(), getTypeName(), getTypeDescription());
    }

    @Override
    public String toString() {
        return "AssessmentType{" +
                "assessmentId=" + assessmentTypeId +
                ", typeName='" + typeName + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                '}';
    }
}
