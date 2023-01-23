package com.hubformath.mathhubservice.services.config;

import com.hubformath.mathhubservice.models.config.Grade;

import java.util.List;

public interface IGradeService {

    List<Grade> getAllGrades();

    Grade getGradeById(Long id);

    Grade createGrade(Grade gradeRequest);

    Grade updateGrade(Long id, Grade grade);

    void deleteGrade(Long id);


}
