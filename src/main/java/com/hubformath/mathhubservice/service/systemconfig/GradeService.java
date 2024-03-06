package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.repository.systemconfig.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(final GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeById(String gradeId) {
        return gradeRepository.findById(gradeId).orElseThrow();
    }

    public Grade createGrade(Grade gradeRequest) {
        return gradeRepository.save(gradeRequest);
    }

    public Grade updateGrade(String gradeId, Grade gradeRequest) {
        return gradeRepository.findById(gradeId)
                              .map(grade -> {
                                  Optional.ofNullable(gradeRequest.getGradeName()).ifPresent(grade::setGradeName);
                                  Optional.ofNullable(gradeRequest.getGradeDescription())
                                          .ifPresent(grade::setGradeDescription);
                                  return gradeRepository.save(grade);
                              })
                              .orElseThrow();
    }

    public void deleteGrade(String gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                                     .orElseThrow();

        gradeRepository.delete(grade);
    }
}

