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
@Table(name = "class_rates")
public class ClassRate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "amount", nullable = false)
    private Double amount;

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
    public ClassRate() {
        // Used by hibernate instantiation
    }

    public ClassRate(
            final Double amount,
            final SubjectComplexity subjectComplexity,
            final Instant effectiveDate,
            final Instant expiryDate) {
        this.amount = amount;
        this.subjectComplexity = subjectComplexity;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
        if (!(o instanceof ClassRate that)) return false;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getAmount(), that.getAmount())
                && getSubjectComplexity() == that.getSubjectComplexity()
                && Objects.equals(getEffectiveDate(), that.getEffectiveDate())
                && Objects.equals(getExpiryDate(), that.getExpiryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                            getAmount(),
                            getSubjectComplexity(),
                            getEffectiveDate(),
                            getExpiryDate());
    }

    @Override
    public String toString() {
        return "ClassRate{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", complexity=" + subjectComplexity +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
