package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.repository.systemconfig.GradeRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    private final String notFoundItemName;

    public GradeService(final GradeRepository gradeRepository){
        super();
        this.gradeRepository = gradeRepository;
        this.notFoundItemName = "grade";
    }

    public List<Grade>getAllGrades() {return gradeRepository.findAll(); }

    public Grade getGradeById(Long id){
        Optional<Grade> grade = gradeRepository.findById(id);

        if (grade.isPresent()){
            return grade.get();
        }else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }


    public Grade createGrade(Grade gradeRequest) {return gradeRepository.save(gradeRequest); }

    public  Grade updateGrade(Long id, Grade gradeRequest){
        return gradeRepository.findById(id).map(grade -> {
            grade.setGradeName(gradeRequest.getGradeName());
            grade.setSubjects(gradeRequest.getSubjects());
            return gradeRepository.save(grade);
        })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteGrade(Long id){
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        gradeRepository.delete(grade);
    }
}

