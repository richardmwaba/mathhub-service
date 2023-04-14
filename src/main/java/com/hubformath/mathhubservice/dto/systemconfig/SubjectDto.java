package com.hubformath.mathhubservice.dto.systemconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;

import java.util.UUID;

public class SubjectDto {

    private UUID subjectId;

    private String subjectName;

    private GradeDto subjectGrade;

    private UUID subjectGradeId;

    private SubjectComplexity subjectComplexity;

    public UUID getSubjectId() {return subjectId; }

    public void setSubjectId(UUID subjectId) {this.subjectId = subjectId;}


    public String getSubjectName() {return subjectName;}

    public void setSubjectName(String subjectName) {this.subjectName = subjectName;}


    public GradeDto getSubjectGrade() {return subjectGrade;}

    public void setSubjectGrade(GradeDto subjectGrade) {this.subjectGrade = subjectGrade;}

    @JsonIgnore
    public UUID getSubjectGradeId() {
        return subjectGradeId;
    }

    @JsonProperty
    public void setSubjectGradeId(UUID subjectGradeId) {
        this.subjectGradeId = subjectGradeId;
    }

    public SubjectComplexity getSubjectComplexity() {
        return subjectComplexity;
    }

    public void setSubjectComplexity(SubjectComplexity subjectComplexity) {
        this.subjectComplexity = subjectComplexity;
    }
}
