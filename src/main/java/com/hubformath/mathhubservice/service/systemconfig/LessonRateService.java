package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.LessonRateRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class LessonRateService {

    private final LessonRateRepository lessonRateRepository;

    public LessonRateService(final LessonRateRepository lessonRateRepository) {
        this.lessonRateRepository = lessonRateRepository;
    }

    public List<LessonRate> getAllLessonRates(final Instant effectiveDate, final Instant expiryDate) {
        if (effectiveDate != null && expiryDate != null) {
            return lessonRateRepository.findAll()
                                       .stream()
                                       .filter(lessonRate -> isValidLessonRate(effectiveDate, expiryDate, lessonRate))
                                       .toList();
        }

        final Instant instantNow = Instant.now();

        return lessonRateRepository.findAll()
                                   .stream()
                                   .filter(lessonRate -> isExpiredDateEqualToOrAfterGivenDate(lessonRate, instantNow))
                                   .toList();
    }

    private static boolean isExpiredDateEqualToOrAfterGivenDate(LessonRate lessonRate, Instant instantNow) {
        return lessonRate.getExpiredDate()
                         .equals(instantNow) || lessonRate.getExpiredDate()
                                                          .isAfter(instantNow);
    }

    private static boolean isValidLessonRate(Instant effectiveDate, Instant expiryDate, LessonRate lessonRate) {
        return isEffectiveDateEqualToOrAfterGivenDate(effectiveDate, lessonRate)
                && isExpiryDateEqualToOrBeforeGivenDate(expiryDate, lessonRate);
    }

    private static boolean isExpiryDateEqualToOrBeforeGivenDate(Instant expiryDate, LessonRate lessonRate) {
        return lessonRate.getExpiredDate().isBefore(expiryDate) || lessonRate.getExpiredDate().equals(expiryDate);
    }

    private static boolean isEffectiveDateEqualToOrAfterGivenDate(Instant effectiveDate, LessonRate lessonRate) {
        return lessonRate.getEffectiveDate().equals(effectiveDate) || lessonRate.getEffectiveDate()
                                                                                .isAfter(effectiveDate);
    }

    public LessonRate getLessonRateById(final String lessonRateId) {
        return lessonRateRepository.findById(lessonRateId).orElseThrow();
    }

    public LessonRate getLessonRateBySubjectComplexity(final SubjectComplexity subjectComplexity) {
        final Instant instantNow = Instant.now();
        return lessonRateRepository.findAll()
                                   .stream()
                                   .filter(lessonRate -> isExpiredDateEqualToOrAfterGivenDate(lessonRate, instantNow)
                                           && lessonRate.getSubjectComplexity().equals(subjectComplexity))
                                   .findAny().orElse(null);
    }

    public LessonRate createLessonRate(final LessonRate lessonRate) {
        return lessonRateRepository.save(lessonRate);
    }
}
