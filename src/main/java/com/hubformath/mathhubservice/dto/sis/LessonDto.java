package com.hubformath.mathhubservice.dto.sis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.dto.systemconfig.SubjectDto;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.sis.LessonPeriod;
import com.hubformath.mathhubservice.model.sis.SessionType;

import java.time.LocalDate;
import java.util.UUID;

public class LessonDto {

    private UUID lessonId;

    private SubjectDto subject;

    private UUID subjectId;

    private int occurrence;

    private LocalDate lessonStartDate;

    private int lessonDuration;

    private LessonPeriod lessonPeriod;

    private SessionType sessionType;

    private Double lessonRateAmount;

    private PaymentStatus lessonPaymentStatus;

    public UUID getLessonId() {
        return lessonId;
    }

    public void setLessonId(UUID lessonId) {
        this.lessonId = lessonId;
    }

    @JsonProperty
    public SubjectDto getSubject() {
        return subject;
    }

    @JsonIgnore
    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    @JsonProperty
    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    @JsonIgnore
    public UUID getSubjectId() {
        return subjectId;
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

    public Double getLessonRateAmount() {
        return lessonRateAmount;
    }

    public void setLessonRateAmount(Double lessonRateAmount) {
        this.lessonRateAmount = lessonRateAmount;
    }

    public PaymentStatus getLessonPaymentStatus() {
        return lessonPaymentStatus;
    }

    public void setLessonPaymentStatus(PaymentStatus lessonPaymentStatus) {
        this.lessonPaymentStatus = lessonPaymentStatus;
    }
}
