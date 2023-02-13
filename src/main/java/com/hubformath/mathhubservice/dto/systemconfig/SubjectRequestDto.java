package com.hubformath.mathhubservice.dto.systemconfig;

public class SubjectRequestDto {

    private String subjectName;

    private Long subjectGradeId;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getSubjectGradeId() {
        return subjectGradeId;
    }

    public void setSubjectGradeId(Long subjectGradeId) {
        this.subjectGradeId = subjectGradeId;
    }
}
