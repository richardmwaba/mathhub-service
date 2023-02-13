package com.hubformath.mathhubservice.service.systemconfig.impl;

import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.repository.systemconfig.GradeRepository;
import com.hubformath.mathhubservice.service.systemconfig.IGradeService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiveImpl implements IGradeService {

    private final GradeRepository gradeRepository;

    private final String notFoundItemName;

    public GradeServiveImpl(GradeRepository gradeRepository){
        super();
        this.gradeRepository = gradeRepository;
        this.notFoundItemName = "grade";
    }

    @Override
    public List<Grade>getAllGrades() {return gradeRepository.findAll(); }

    @Override
    public Grade getGradeById(Long id){
        Optional<Grade> grade = gradeRepository.findById(id);

        if (grade.isPresent()){
            return grade.get();
        }else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }


    @Override
    public Grade createGrade(Grade gradeRequest) {return gradeRepository.save(gradeRequest); }

    @Override
    public  Grade updateGrade(Long id, Grade gradeRequest){
        return gradeRepository.findById(id).map(grade -> {
            grade.setGradeName(gradeRequest.getGradeName());
            grade.setSubjects(gradeRequest.getSubjects());
            return gradeRepository.save(grade);
        })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteGrade(Long id){
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        gradeRepository.delete(grade);
    }
}

