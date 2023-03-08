package com.hubformath.mathhubservice.model.systemconfig;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class LessonRate {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    private Double amountPerLesson;

    private Instant effectiveDate;

    private Instant expiredDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public LessonRate(Double amountPerLesson, Instant effectiveDate, Instant expiredDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.amountPerLesson = amountPerLesson;
        this.effectiveDate = effectiveDate;
        this.expiredDate = expiredDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountPerLesson() {
        return amountPerLesson;
    }

    public void setAmountPerLesson(Double amountPerLesson) {
        this.amountPerLesson = amountPerLesson;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
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
        if (!(o instanceof LessonRate lessonRate))
            return false;
        return Objects.equals(this.id, lessonRate.id)
                && Objects.equals(this.amountPerLesson, lessonRate.amountPerLesson)
                && Objects.equals(this.effectiveDate, lessonRate.effectiveDate)
                && Objects.equals(this.expiredDate, lessonRate.expiredDate)
                && Objects.equals(this.createdAt, lessonRate.createdAt)
                && Objects.equals(this.updatedAt, lessonRate.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountPerLesson, effectiveDate, expiredDate, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "LessonRate{" +
                "id=" + id +
                ", amountPerLesson=" + amountPerLesson +
                ", effectiveDate=" + effectiveDate +
                ", expiredDate=" + expiredDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
