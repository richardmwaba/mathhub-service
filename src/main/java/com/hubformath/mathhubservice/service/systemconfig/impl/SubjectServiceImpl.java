package com.hubformath.mathhubservice.service.systemconfig.impl;

import com.hubformath.mathhubservice.dto.systemconfig.SubjectRequestDto;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.repository.systemconfig.SubjectRepository;
import com.hubformath.mathhubservice.service.systemconfig.IGradeService;
import com.hubformath.mathhubservice.service.systemconfig.ISubjectService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements ISubjectService {

    @Autowired
    IGradeService gradeService;

    private final SubjectRepository subjectRepository;

    private final String notFoundItemName;

    public SubjectServiceImpl(SubjectRepository subjectRepository){
        super();
        this.subjectRepository = subjectRepository;
        this.notFoundItemName = "subject";
    }

    @Override
    public List<Subject> getAllSubjects() { return subjectRepository.findAll(); }

    @Override
    public Subject getSubjectById(Long id){
        Optional<Subject> subject = subjectRepository.findById(id);

        if (subject.isPresent()){
            return subject.get();
        }else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Subject createSubject(SubjectRequestDto subjectRequest){
        final long subjectGradeId = subjectRequest.getSubjectGradeId();
        final String subjectName = subjectRequest.getSubjectName();
        final Grade grade = gradeService.getGradeById(subjectGradeId);

        final Subject newSubject = new Subject(subjectName);
        newSubject.setSubjectGrade(grade);


        return subjectRepository.save(newSubject);
    }

    @Override
    public Subject updateSubject(Long id, Subject subjectRequest) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setSubjectName(subjectRequest.getSubjectName());
                    subject.setSubjectGrade(subjectRequest.getSubjectGrade());
                    return subjectRepository.save(subject);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        subjectRepository.delete(subject);
    }

}
