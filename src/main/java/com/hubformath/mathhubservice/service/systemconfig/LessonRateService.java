package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.repository.systemconfig.LessonRateRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonRateService {
    
    private final LessonRateRepository lessonRateRepository;

    private final String notFoundItemName;

    public LessonRateService(LessonRateRepository lessonRateRepository, String notFoundItemName) {
        this.lessonRateRepository = lessonRateRepository;
        this.notFoundItemName = notFoundItemName;
    }

    //TODO: Add filter by effectiveDate and expiredDate
    public List<LessonRate> getAllLessonRates() { return lessonRateRepository.findAll(); }

    public LessonRate getLessonRateById(Long id){
        Optional<LessonRate> lessonRate = lessonRateRepository.findById(id);

        if (lessonRate.isPresent()){
            return lessonRate.get();
        }else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public LessonRate createLessonRate(LessonRate lessonRate){
        return lessonRateRepository.save(lessonRate);
    }

    public void deleteLessonRate(Long id) {
        LessonRate lessonRate = lessonRateRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        lessonRateRepository.delete(lessonRate);
    }
}
