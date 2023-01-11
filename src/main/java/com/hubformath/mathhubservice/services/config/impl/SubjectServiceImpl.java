package com.hubformath.mathhubservice.services.config.impl;

import com.hubformath.mathhubservice.models.config.Subject;
import com.hubformath.mathhubservice.repositories.config.SubjectRepository;
import com.hubformath.mathhubservice.services.config.ISubjectService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements ISubjectService {

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
    public Subject createSubject(Subject subjectRequest){
        return subjectRepository.save(subjectRequest);
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
