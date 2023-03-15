package com.hubformath.mathhubservice.model.sis;

import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ReadOnlyProperty
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private int occurrence;

    private LocalDate lessonStartDate;

    private Double lessonRateAmount;

    private int lessonDuration;

    @Enumerated(EnumType.STRING)
    private LessonPeriod lessonPeriod;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus lessonPaymentStatus;

    @CreationTimestamp
    @ReadOnlyProperty
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @ReadOnlyProperty
    private LocalDateTime updatedAt;

    public Lesson(final Subject subject,
                  final int occurrence,
                  final LocalDate lessonStartDate,
                  final Double lessonRateAmount,
                  final int lessonDuration,
                  final LessonPeriod lessonPeriod,
                  SessionType sessionType) {
        this.subject = subject;
        this.occurrence = occurrence;
        this.lessonStartDate = lessonStartDate;
        this.lessonRateAmount = lessonRateAmount;
        this.lessonDuration = lessonDuration;
        this.lessonPeriod = lessonPeriod;
        this.sessionType = sessionType;
        this.lessonPaymentStatus = PaymentStatus.UNPAID;
    }

    public Lesson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public LocalDate getLessonStartDate() {
        return lessonStartDate;
    }

    public void setLessonStartDate(LocalDate lessonStartDate) {
        this.lessonStartDate = lessonStartDate;
    }

    public Double getLessonRateAmount() {
        return lessonRateAmount;
    }

    public void setLessonRateAmount(Double lessonRateAmount) {
        this.lessonRateAmount = lessonRateAmount;
    }

    public int getLessonDuration() {
        return lessonDuration;
    }

    public void setLessonDuration(int lessonDuration) {
        this.lessonDuration = lessonDuration;
    }

    public LessonPeriod getLessonPeriod() {
        return lessonPeriod;
    }

    public void setLessonPeriod(LessonPeriod lessonPeriod) {
        this.lessonPeriod = lessonPeriod;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public PaymentStatus getLessonPaymentStatus() {
        return lessonPaymentStatus;
    }

    public void setLessonPaymentStatus(PaymentStatus lessonPaymentStatus) {
        this.lessonPaymentStatus = lessonPaymentStatus;
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
        if (!(o instanceof Lesson lesson))
            return false;
        return Objects.equals(this.id, lesson.id) && Objects.equals(this.occurrence, lesson.occurrence)
                && Objects.equals(this.lessonStartDate, lesson.lessonStartDate)
                && Objects.equals(this.lessonRateAmount, lesson.lessonRateAmount)
                && Objects.equals(this.lessonDuration, lesson.lessonDuration)
                && Objects.equals(this.lessonPeriod, lesson.lessonPeriod)
                && Objects.equals(this.sessionType, lesson.sessionType)
                && Objects.equals(this.lessonPaymentStatus, lesson.lessonPaymentStatus)
                && Objects.equals(this.createdAt, lesson.createdAt)
                && Objects.equals(this.updatedAt, lesson.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.occurrence,
                this.lessonStartDate,
                this.lessonRateAmount,
                this.lessonDuration,
                this.sessionType,
                this.lessonPaymentStatus,
                this.lessonPeriod,
                this.createdAt,
                this.updatedAt);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subject=" + subject +
                ", occurrence=" + occurrence +
                ", lessonStartDate=" + lessonStartDate +
                ", lessonRate=" + lessonRateAmount +
                ", lessonDuration=" + lessonDuration +
                ", lessonPeriod=" + lessonPeriod +
                ", sessionType=" + sessionType +
                ", lessonPaymentStatus=" + lessonPaymentStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}