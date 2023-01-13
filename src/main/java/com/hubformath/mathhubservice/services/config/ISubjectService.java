package com.hubformath.mathhubservice.services.config;


import com.hubformath.mathhubservice.dtos.config.SubjectRequestDto;
import com.hubformath.mathhubservice.models.config.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    Subject createSubject(SubjectRequestDto subjectRequestDto);

    Subject getSubjectById(Long id);

    Subject updateSubject(Long id, Subject subject);

    void  deleteSubject(Long id);
}
