package com.hubformath.mathhubservice.services.config;

import com.hubformath.mathhubservice.models.config.Syllabus;

import java.util.List;

public interface ISyllabusService {

    List<Syllabus> getAllSyllabi();

    Syllabus createSyllabus(Syllabus syllabus);

    Syllabus getSyllabusById(Long id);

    Syllabus updateSyllabus(Long  id, Syllabus syllabus);

    void deleteSyllabus(Long id);
}
