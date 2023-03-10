package com.hubformath.mathhubservice.dto.systemconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubformath.mathhubservice.model.systemconfig.SubjectComplexity;

public class SubjectDto {

    private Long id;

    private String subjectName;

    private GradeDto subjectGrade;

    private Long subjectGradeId;

    private SubjectComplexity subjectComplexity;

    public Long getId() {return id; }

    public void setId(Long id) {this.id = id;}


    public String getSubjectName() {return subjectName;}

    public void setSubjectName(String subjectName) {this.subjectName = subjectName;}


    public GradeDto getSubjectGrade() {return subjectGrade;}

    public void setSubjectGrade(GradeDto subjectGrade) {this.subjectGrade = subjectGrade;}

    @JsonIgnore
    public Long getSubjectGradeId() {
        return subjectGradeId;
    }

    @JsonProperty
    public void setSubjectGradeId(Long subjectGradeId) {
        this.subjectGradeId = subjectGradeId;
    }

    @JsonIgnore
    public SubjectComplexity getSubjectComplexity() {
        return subjectComplexity;
    }

    @JsonProperty
    public void setSubjectComplexity(SubjectComplexity subjectComplexity) {
        this.subjectComplexity = subjectComplexity;
    }
}
