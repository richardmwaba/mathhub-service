package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.sis.SubjectRequest;
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
        if (subjectRepository.existsByName(subjectRequest.name())) {
            throw new IllegalStateException("Subject with name " + subjectRequest.name() + " already exists. If you want to update it, please use the update method.");
        }

        final String subjectName = subjectRequest.name();
        final SubjectComplexity subjectComplexity = subjectRequest.complexity();
        final Set<Grade> grades = extractGrades(subjectRequest.gradeIds());

        final Subject newSubject = new Subject(subjectName, subjectComplexity, grades);

        return subjectRepository.save(newSubject);
    }

    public Subject updateSubject(String subjectId, SubjectRequest subjectRequest) {
        Subject updatedSubject = subjectRepository.findById(subjectId)
                                                  .map(subject -> {
                                                      Optional.ofNullable(subjectRequest.name())
                                                              .ifPresent(subject::setName);
                                                      Optional.ofNullable(subjectRequest.complexity())
                                                              .ifPresent(subject::setComplexity);
                                                      Optional.ofNullable(subjectRequest.gradeIds())
                                                              .ifPresent(gradeIds -> subject.setGrades(
                                                                      extractGrades(gradeIds)));

                                                      return subject;
                                                  }).orElseThrow();

        return subjectRepository.save(updatedSubject);
    }

    public void deleteSubject(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                                           .orElseThrow();

        subjectRepository.delete(subject);
    }

    private Set<Grade> extractGrades(Set<String> gradeIds) {
        return gradeIds.stream().map(gradeService::getGradeById).collect(Collectors.toSet());
    }
}
