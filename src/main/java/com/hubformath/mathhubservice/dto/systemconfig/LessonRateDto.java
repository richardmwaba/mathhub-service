package com.hubformath.mathhubservice.dto.systemconfig;

import java.time.Instant;

public class LessonRateDto {
    private Long id;

    private Double amountPerLesson;

    private Instant effectiveDate;

    private Instant expiredDate;

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
}
