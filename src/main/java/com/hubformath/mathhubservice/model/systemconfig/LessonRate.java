package com.hubformath.mathhubservice.model.systemconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "lesson_rates")
public class LessonRate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "lesson_rate_id", updatable = false, nullable = false)
    private String lessonRateId;

    @Column(name = "amount_per_lesson", nullable = false)
    private Double amountPerLesson;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_complexity", nullable = false)
    private SubjectComplexity subjectComplexity;

    @Column(name = "effective_date", nullable = false)
    private Instant effectiveDate;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    public LessonRate() {
        // Used by hibernate instantiation
    }

    public LessonRate(
            final Double amountPerLesson,
            final SubjectComplexity subjectComplexity,
            final Instant effectiveDate,
            final Instant expiryDate) {
        this.amountPerLesson = amountPerLesson;
        this.subjectComplexity = subjectComplexity;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
    }

    public String getLessonRateId() {
        return lessonRateId;
    }

    public void setLessonRateId(String lessonRateId) {
        this.lessonRateId = lessonRateId;
    }

    public Double getAmountPerLesson() {
        return amountPerLesson;
    }

    public void setAmountPerLesson(Double amountPerLesson) {
        this.amountPerLesson = amountPerLesson;
    }

    public SubjectComplexity getSubjectComplexity() {
        return subjectComplexity;
    }

    public void setSubjectComplexity(SubjectComplexity subjectComplexity) {
        this.subjectComplexity = subjectComplexity;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
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
        if (!(o instanceof LessonRate that)) return false;
        return Objects.equals(getLessonRateId(), that.getLessonRateId())
                && Objects.equals(getAmountPerLesson(), that.getAmountPerLesson())
                && getSubjectComplexity() == that.getSubjectComplexity()
                && Objects.equals(getEffectiveDate(), that.getEffectiveDate())
                && Objects.equals(getExpiryDate(), that.getExpiryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLessonRateId(),
                            getAmountPerLesson(),
                            getSubjectComplexity(),
                            getEffectiveDate(),
                            getExpiryDate());
    }

    @Override
    public String toString() {
        return "LessonRate{" +
                "lessonRateId=" + lessonRateId +
                ", amountPerLesson=" + amountPerLesson +
                ", subjectComplexity=" + subjectComplexity +
                ", effectiveDate=" + effectiveDate +
                ", expiredDate=" + expiryDate +
                '}';
    }
}
