package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.LessonRateRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LessonRateService {
    
    private final LessonRateRepository lessonRateRepository;

    private final String notFoundItemName;

    public LessonRateService(final LessonRateRepository lessonRateRepository, final String notFoundItemName) {
        this.lessonRateRepository = lessonRateRepository;
        this.notFoundItemName = notFoundItemName;
    }

    public List<LessonRate> getAllLessonRates(final Instant effectiveDate, final Instant expiryDate) {
        if (effectiveDate != null && expiryDate != null) {
            return lessonRateRepository.findAll()
                    .stream()
                    .filter(lessonRate ->
                            (lessonRate.getEffectiveDate().equals(effectiveDate) || lessonRate.getEffectiveDate().isAfter(effectiveDate))
                            && (lessonRate.getExpiryDate().isBefore(expiryDate) || lessonRate.getExpiryDate().equals(expiryDate))
                    ).toList();
        }

        final Instant instantNow = Instant.now();

        return lessonRateRepository.findAll()
                .stream()
                .filter(lessonRate -> lessonRate.getExpiryDate().equals(instantNow) || lessonRate.getExpiryDate().isAfter(instantNow))
                .toList();
    }

    public LessonRate getLessonRateById(final Long id){
        Optional<LessonRate> lessonRate = lessonRateRepository.findById(id);

        if (lessonRate.isPresent()){
            return lessonRate.get();
        }else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public LessonRate getLessonRateBySubjectComplexity(final SubjectComplexity subjectComplexity) {
        final Instant instantNow = Instant.now();
        return lessonRateRepository.findAll()
                .stream()
                .filter(lessonRate ->
                        (lessonRate.getExpiryDate().equals(instantNow) || lessonRate.getExpiryDate().isAfter(instantNow))
                        && lessonRate.getSubjectComplexity().equals(subjectComplexity))
                .findAny().orElse(null);
    }

    public LessonRate createLessonRate(final LessonRate lessonRate){
        return lessonRateRepository.save(lessonRate);
    }
}
