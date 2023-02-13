package com.hubformath.mathhubservice.service.systemconfig;


import com.hubformath.mathhubservice.dto.systemconfig.SubjectRequestDto;
import com.hubformath.mathhubservice.model.systemconfig.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    Subject createSubject(SubjectRequestDto subjectRequestDto);

    Subject getSubjectById(Long id);

    Subject updateSubject(Long id, Subject subject);

    void  deleteSubject(Long id);
}
