package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.dto.systemconfig.SubjectDto;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.SubjectRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    GradeService gradeService;

    private final SubjectRepository subjectRepository;

    private final String notFoundItemName;

    public SubjectService(final GradeService gradeService, final SubjectRepository subjectRepository){
        this.gradeService = gradeService;
        this.subjectRepository = subjectRepository;
        this.notFoundItemName = "subject";
    }

    public List<Subject> getAllSubjects() { return subjectRepository.findAll(); }

    public Subject getSubjectById(Long id){
        Optional<Subject> subject = subjectRepository.findById(id);

        if (subject.isPresent()){
            return subject.get();
        }else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        subjectRepository.delete(subject);
    }

}
