package com.hubformath.mathhubservice.service.sis;

import java.util.List;

import com.hubformath.mathhubservice.model.sis.Lessons;

public interface ILessonsService {
    List<Lessons> getAllLessons();

    Lessons createLessons(Lessons lessonsRequest);

    Lessons getLessonsById(Long id);

    Lessons updateLessons(Long id, Lessons lessons);

    void deleteLessons(Long id);
}
