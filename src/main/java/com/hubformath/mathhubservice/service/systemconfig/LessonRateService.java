package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.LessonRateRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LessonRateService {

    private final LessonRateRepository lessonRateRepository;

    public LessonRateService(final LessonRateRepository lessonRateRepository) {
        this.lessonRateRepository = lessonRateRepository;
    }

    public LessonRate createLessonRate(LessonRate lessonRate) {
        return lessonRateRepository.save(lessonRate);
    }

    public LessonRate getLessonRateById(String lessonRateId) {
        return lessonRateRepository.findById(lessonRateId).orElseThrow();
    }

    public LessonRate updateLessonRate(String lessonRateId, LessonRate lessonRateRequest) {
        return lessonRateRepository.findById(lessonRateId)
                                   .map(lessonRate -> {
                                       Optional.of(lessonRateRequest.getExpiryDate())
                                               .ifPresent(lessonRate::setExpiryDate);
                                       return lessonRateRepository.save(lessonRate);
                                   })
                                   .orElseThrow();
    }

    public LessonRate getLessonRateBySubjectComplexity(SubjectComplexity subjectComplexity) {
        final Instant instantNow = Instant.now();
        return lessonRateRepository.findAll()
                                   .stream()
                                   .filter(lessonRate -> isExpiredDateEqualToOrAfterGivenDate(lessonRate, instantNow)
                                           && lessonRate.getSubjectComplexity().equals(subjectComplexity))
                                   .findAny().orElse(null);
    }

    public List<LessonRate> getAllLessonRates(Instant effectiveDate, Instant expiryDate) {
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
        return lessonRate.getExpiryDate()
                         .equals(instantNow) || lessonRate.getExpiryDate()
                                                          .isAfter(instantNow);
    }

    private static boolean isValidLessonRate(Instant effectiveDate, Instant expiryDate, LessonRate lessonRate) {
        return isEffectiveDateEqualToOrAfterGivenDate(effectiveDate, lessonRate)
                && isExpiryDateEqualToOrBeforeGivenDate(expiryDate, lessonRate);
    }

    private static boolean isExpiryDateEqualToOrBeforeGivenDate(Instant expiryDate, LessonRate lessonRate) {
        return lessonRate.getExpiryDate().isBefore(expiryDate) || lessonRate.getExpiryDate().equals(expiryDate);
    }

    private static boolean isEffectiveDateEqualToOrAfterGivenDate(Instant effectiveDate, LessonRate lessonRate) {
        return lessonRate.getEffectiveDate().equals(effectiveDate) || lessonRate.getEffectiveDate()
                                                                                .isAfter(effectiveDate);
    }
}
