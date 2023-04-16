package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.dto.systemconfig.SubjectDto;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {

    private final GradeService gradeService;

    private final SubjectRepository subjectRepository;

    public SubjectService(final GradeService gradeService, final SubjectRepository subjectRepository){
        this.gradeService = gradeService;
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() { return subjectRepository.findAll(); }

    public Subject getSubjectById(UUID subjectId){
        return subjectRepository.findById(subjectId).orElseThrow();
    }

    public Subject createSubject(SubjectDto subjectRequest){
        final UUID subjectGradeId = subjectRequest.getSubjectGradeId();
        final String subjectName = subjectRequest.getSubjectName();
        final SubjectComplexity subjectComplexity = subjectRequest.getSubjectComplexity();
        final Grade grade = gradeService.getGradeById(subjectGradeId);

        final Subject newSubject = new Subject(subjectName, subjectComplexity);
        newSubject.setSubjectGrade(grade);


        return subjectRepository.save(newSubject);
    }

    public Subject updateSubject(UUID subjectId, Subject subjectRequest) {
        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    Optional.ofNullable(subjectRequest.getSubjectName()).ifPresent(subject::setSubjectName);
                    Optional.ofNullable(subjectRequest.getSubjectComplexity()).ifPresent(subject::setSubjectComplexity);
                    return subjectRepository.save(subject);
                })
                .orElseThrow();
    }

    public void deleteSubject(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow();

        subjectRepository.delete(subject);
    }

}
