package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.dto.systemconfig.SubjectDto;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final GradeService gradeService;

    private final SubjectRepository subjectRepository;

    public SubjectService(final GradeService gradeService, final SubjectRepository subjectRepository){
        this.gradeService = gradeService;
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() { return subjectRepository.findAll(); }

    public Subject getSubjectById(Long id){
        return subjectRepository.findById(id).orElseThrow();
    }

    public Subject createSubject(SubjectDto subjectRequest){
        final long subjectGradeId = subjectRequest.getSubjectGradeId();
        final String subjectName = subjectRequest.getSubjectName();
        final SubjectComplexity subjectComplexity = subjectRequest.getSubjectComplexity();
        final Grade grade = gradeService.getGradeById(subjectGradeId);

        final Subject newSubject = new Subject(subjectName, subjectComplexity);
        newSubject.setSubjectGrade(grade);


        return subjectRepository.save(newSubject);
    }

    public Subject updateSubject(Long id, Subject subjectRequest) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setSubjectName(subjectRequest.getSubjectName());
                    subject.setSubjectGrade(subjectRequest.getSubjectGrade());
                    return subjectRepository.save(subject);
                })
                .orElseThrow();
    }

    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow();

        subjectRepository.delete(subject);
    }

}
