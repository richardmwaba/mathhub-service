package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.repository.systemconfig.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(final GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id).orElseThrow();
    }

    public Grade createGrade(Grade gradeRequest) {
        return gradeRepository.save(gradeRequest);
    }

    public Grade updateGrade(Long id, Grade gradeRequest) {
        return gradeRepository.findById(id)
                .map(grade -> {
                    grade.setGradeName(gradeRequest.getGradeName());
                    grade.setGradeDescription(gradeRequest.getGradeDescription());
                    grade.setSubjects(gradeRequest.getSubjects());
                    return gradeRepository.save(grade);
                })
                .orElseThrow();
    }

    public void deleteGrade(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow();

        gradeRepository.delete(grade);
    }
}

