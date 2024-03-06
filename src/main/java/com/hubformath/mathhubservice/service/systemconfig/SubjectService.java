package com.hubformath.mathhubservice.service.systemconfig;

import com.google.common.collect.Sets;
import com.hubformath.mathhubservice.dto.systemconfig.SubjectRequest;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;
import com.hubformath.mathhubservice.repository.systemconfig.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final GradeService gradeService;

    private final SubjectRepository subjectRepository;

    public SubjectService(final GradeService gradeService, final SubjectRepository subjectRepository) {
        this.gradeService = gradeService;
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(String subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow();
    }

    public Subject createSubject(SubjectRequest subjectRequest) {
        if(subjectRepository.existsBySubjectName(subjectRequest.subjectName())) {
            throw new IllegalStateException("Subject with name " + subjectRequest.subjectName() + " already exists. If you want to update it, please use the update method.");
        }

        final String subjectName = subjectRequest.subjectName();
        final SubjectComplexity subjectComplexity = subjectRequest.subjectComplexity();
        final Set<Grade> grades = extractGrades(subjectRequest.subjectGradeIds());

        final Subject newSubject = new Subject(subjectName, subjectComplexity, grades);

        return subjectRepository.save(newSubject);
    }

    public Subject updateSubject(String subjectId, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow();

        Optional.ofNullable(subjectRequest.subjectName())
                .ifPresent(subject::setSubjectName);
        Optional.ofNullable(subjectRequest.subjectComplexity())
                .ifPresent(subject::setSubjectComplexity);

        if (subjectRequest.subjectGradeIds() != null && !subjectRequest.subjectGradeIds().isEmpty()) {
            updateGrades(subject, subjectRequest.subjectGradeIds());
        }

        return subjectRepository.save(subject);
    }

    public void deleteSubject(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                                           .orElseThrow();

        subjectRepository.delete(subject);
    }

    private Set<Grade> extractGrades(Set<String> gradeIds) {
        return gradeIds.stream().map(gradeService::getGradeById).collect(Collectors.toSet());
    }

    private void updateGrades(Subject subject, Set<String> updatedGradeIds) {
        Set<Grade> currentSubjectGrades = subject.getSubjectGrades();
        Set<Grade> newSubjectGrades = extractGrades(updatedGradeIds);

        Sets.SetView<Grade> gradeSetView = Sets.difference(currentSubjectGrades, newSubjectGrades);
        subject.getSubjectGrades().removeAll(gradeSetView);
        subject.getSubjectGrades().addAll(Sets.difference(newSubjectGrades, currentSubjectGrades));
    }

}
