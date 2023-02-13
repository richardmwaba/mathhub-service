package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.Syllabus;

public interface ISyllabusService {

    List<Syllabus> getAllSyllabi();

    Syllabus createSyllabus(Syllabus syllabus);

    Syllabus getSyllabusById(Long id);

    Syllabus updateSyllabus(Long  id, Syllabus syllabus);

    void deleteSyllabus(Long id);
}
