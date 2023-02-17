package com.hubformath.mathhubservice.service.sis.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.sis.Lessons;
import com.hubformath.mathhubservice.repository.sis.LessonsRepository;
import com.hubformath.mathhubservice.service.sis.ILessonsService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class LessonsServiceImpl implements ILessonsService{

    private final LessonsRepository lessonsRepository;

    private final String notFoundItemName;

    public LessonsServiceImpl(LessonsRepository lessonsRepository) {
        super();
        this.lessonsRepository = lessonsRepository;
        this.notFoundItemName = "lessons";
    }

    @Override
    public List<Lessons> getAllLessons() {
        return lessonsRepository.findAll();
    }

    @Override
    public Lessons getLessonsById(Long id) {
        Optional<Lessons> lessons = lessonsRepository.findById(id);

        if(lessons.isPresent()){
            return lessons.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Lessons createLessons(Lessons lessonsRequest) {
        return lessonsRepository.save(lessonsRequest);
    }

    @Override
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

    @Override
    public void deleteLessons(Long id) {
        Lessons lessons = lessonsRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        lessonsRepository.delete(lessons);
    }
}
