package com.hubformath.mathhubservice.service.sis;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.sis.Lesson;
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

    public List<Lesson> getAllLessons() {
        return lessonsRepository.findAll();
    }

    public Lesson getLessonsById(Long id) {
        Optional<Lesson> lessons = lessonsRepository.findById(id);

        if(lessons.isPresent()){
            return lessons.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public Lesson createLessons(Lesson lessonRequest) {
        return lessonsRepository.save(lessonRequest);
    }

    public Lesson updateLessons(Long id, Lesson lessonRequest) {
        return lessonsRepository.findById(id)
                .map(lessons -> {
                    lessons.setOccurrence(lessonRequest.getOccurrence());
                    lessons.setLessonStartDate(lessonRequest.getLessonStartDate());
                    lessons.setLessonDuration(lessonRequest.getLessonDuration());
                    lessons.setLessonPeriod(lessonRequest.getLessonPeriod());
                    lessons.setSessionType(lessonRequest.getSessionType());
                    return lessonsRepository.save(lessons);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteLessons(Long id) {
        Lesson lesson = lessonsRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        lessonsRepository.delete(lesson);
    }
}
