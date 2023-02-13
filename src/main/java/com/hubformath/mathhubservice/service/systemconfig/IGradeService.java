package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.Grade;

public interface IGradeService {

    List<Grade> getAllGrades();

    Grade getGradeById(Long id);

    Grade createGrade(Grade gradeRequest);

    Grade updateGrade(Long id, Grade grade);

    void deleteGrade(Long id);


}
