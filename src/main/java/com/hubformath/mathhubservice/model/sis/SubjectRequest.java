package com.hubformath.mathhubservice.model.sis;

import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;

import java.util.Set;

public record SubjectRequest(String name, Set<String> gradeIds, SubjectComplexity complexity) {
}
