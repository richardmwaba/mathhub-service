package com.hubformath.mathhubservice.dto.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;

import java.time.Instant;
import java.util.UUID;

@SuppressWarnings("unused")
public class LessonRateDto {
    private UUID lessonRateId;

    private Double amountPerLesson;

    private Instant effectiveDate;

    private SubjectComplexity subjectComplexity;

    private Instant expiredDate;

    public UUID getLessonRateId() {
        return lessonRateId;
    }

    public void setLessonRateId(UUID lessonRateId) {
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

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }
}
