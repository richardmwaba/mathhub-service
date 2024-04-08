package com.hubformath.mathhubservice.model.sis;

import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;

import java.util.Set;

public record SubjectRequest(String subjectName, Set<String> subjectGradeIds, SubjectComplexity subjectComplexity) {
}
