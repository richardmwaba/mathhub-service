package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.ClassRate;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.ClassRateRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRateService {

    private final ClassRateRepository classRateRepository;

    public ClassRateService(final ClassRateRepository classRateRepository) {
        this.classRateRepository = classRateRepository;
    }

    public ClassRate createClassRate(ClassRate classRate) {
        return classRateRepository.save(classRate);
    }

    public ClassRate getClassRateById(String classRateId) {
        return classRateRepository.findById(classRateId).orElseThrow();
    }

    public ClassRate updateClassRate(String classRateId, ClassRate classRateRequest) {
        return classRateRepository.findById(classRateId)
                                  .map(classRate -> {
                                       Optional.of(classRateRequest.getExpiryDate())
                                               .ifPresent(classRate::setExpiryDate);
                                       return classRateRepository.save(classRate);
                                   })
                                  .orElseThrow();
    }

    public ClassRate getClassRateBySubjectComplexity(SubjectComplexity subjectComplexity) {
        final Instant instantNow = Instant.now();
        return classRateRepository.findAll()
                                  .stream()
                                  .filter(classRate -> isExpiredDateEqualToOrAfterGivenDate(classRate, instantNow)
                                           && classRate.getSubjectComplexity().equals(subjectComplexity))
                                  .findAny().orElse(null);
    }

    public List<ClassRate> getAllClassRates(Instant effectiveDate, Instant expiryDate) {
        if (effectiveDate != null && expiryDate != null) {
            return classRateRepository.findAll()
                                      .stream()
                                      .filter(lessonRate -> isValidClassRate(effectiveDate, expiryDate, lessonRate))
                                      .toList();
        }

        final Instant instantNow = Instant.now();

        return classRateRepository.findAll()
                                  .stream()
                                  .filter(lessonRate -> isExpiredDateEqualToOrAfterGivenDate(lessonRate, instantNow))
                                  .toList();
    }

    private static boolean isExpiredDateEqualToOrAfterGivenDate(ClassRate classRate, Instant instantNow) {
        return classRate.getExpiryDate()
                        .equals(instantNow) || classRate.getExpiryDate()
                                                        .isAfter(instantNow);
    }

    private static boolean isValidClassRate(Instant effectiveDate, Instant expiryDate, ClassRate classRate) {
        return isEffectiveDateEqualToOrAfterGivenDate(effectiveDate, classRate)
                && isExpiryDateEqualToOrBeforeGivenDate(expiryDate, classRate);
    }

    private static boolean isExpiryDateEqualToOrBeforeGivenDate(Instant expiryDate, ClassRate classRate) {
        return classRate.getExpiryDate().isBefore(expiryDate) || classRate.getExpiryDate().equals(expiryDate);
    }

    private static boolean isEffectiveDateEqualToOrAfterGivenDate(Instant effectiveDate, ClassRate classRate) {
        return classRate.getEffectiveDate().equals(effectiveDate) || classRate.getEffectiveDate()
                                                                              .isAfter(effectiveDate);
    }
}
