package com.hubformath.mathhubservice.service.sis;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.sis.Lessons;
import com.hubformath.mathhubservice.repository.sis.LessonsRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class LessonsService {

    private final LessonsRepository lessonsRepository;

    private final String notFoundItemName;

    public LessonsService(final LessonsRepository lessonsRepository) {
        super();
        this.lessonsRepository = lessonsRepository;
        this.notFoundItemName = "lessons";
    }

    public List<Lessons> getAllLessons() {
        return lessonsRepository.findAll();
    }

    public Lessons getLessonsById(Long id) {
        Optional<Lessons> lessons = lessonsRepository.findById(id);

        if(lessons.isPresent()){
            return lessons.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public Lessons createLessons(Lessons lessonsRequest) {
        return lessonsRepository.save(lessonsRequest);
    }

    public Lessons updateLessons(Long id, Lessons lessonsRequest) {
        return lessonsRepository.findById(id)
                .map(lessons -> {
                    lessons.setNumberOfLessons(lessonsRequest.getNumberOfLessons());
                    lessons.setLessonsStartDate(lessonsRequest.getLessonsStartDate());
                    lessons.setLessonsDuration(lessonsRequest.getLessonsDuration());
                    lessons.setLessonsPeriod(lessonsRequest.getLessonsPeriod());
                    lessons.setSessionType(lessonsRequest.getSessionType());
                    return lessonsRepository.save(lessons);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteLessons(Long id) {
        Lessons lessons = lessonsRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        lessonsRepository.delete(lessons);
    }
}
