package com.hubformath.mathhubservice.model.sis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int numberOfLessons;

    private LocalDate lessonsStartDate;

    private int lessonsDuration;

    private LessonsPeriod lessonsPeriod;

    private SessionType sessionType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Lessons(int numberOfLessons, LocalDate lessonsStartDate, int lessonsDuration, LessonsPeriod lessonsPeriod,
                   SessionType sessionType) {
        this.numberOfLessons = numberOfLessons;
        this.lessonsStartDate = lessonsStartDate;
        this.lessonsDuration = lessonsDuration;
        this.lessonsPeriod = lessonsPeriod;
        this.sessionType = sessionType;
    }

    public Lessons(){}

    public Long getId() {
        return id;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(int numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public LocalDate getLessonsStartDate() {
        return lessonsStartDate;
    }

    public void setLessonsStartDate(LocalDate lessonsStartDate) {
        this.lessonsStartDate = lessonsStartDate;
    }

    public int getLessonsDuration() {
        return lessonsDuration;
    }

    public void setLessonsDuration(int lessonsDuration) {
        this.lessonsDuration = lessonsDuration;
    }

    public LessonsPeriod getLessonsPeriod() {
        return lessonsPeriod;
    }

    public void setLessonsPeriod(LessonsPeriod lessonsPeriod) {
        this.lessonsPeriod = lessonsPeriod;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
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
        if (!(o instanceof Lessons))
            return false;
        Lessons lessons = (Lessons) o;
        return Objects.equals(this.id, lessons.id) && Objects.equals(this.numberOfLessons, lessons.numberOfLessons)
                && Objects.equals(this.lessonsStartDate, lessons.lessonsStartDate)
                && Objects.equals(this.lessonsDuration, lessons.lessonsDuration)
                && Objects.equals(this.lessonsPeriod, lessons.lessonsPeriod)
                && Objects.equals(this.sessionType, lessons.sessionType)
                && Objects.equals(this.createdAt, lessons.createdAt)
                && Objects.equals(this.updatedAt, lessons.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.numberOfLessons, this.lessonsStartDate, this.lessonsDuration,
                this.sessionType,
                this.lessonsPeriod, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Lessons {id=" + this.id + ", numberOfLessons=" + this.numberOfLessons + ", lessonsStartDate="
                + this.lessonsStartDate
                + ", LastName="
                + this.lessonsDuration + ", lessonsPeriods=" + this.lessonsPeriod + ", sessionType=" + this.sessionType
                + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }
}