package com.hubformath.mathhubservice.services.config;

import com.hubformath.mathhubservice.models.config.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    Subject createSubject(Subject subject);

    Subject getSubjectById(Long id);

    Subject updateSubject(Long id, Subject subject);

    void  deleteSubject(Long id);
}
