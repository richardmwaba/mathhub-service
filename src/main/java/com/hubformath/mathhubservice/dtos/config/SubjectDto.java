package com.hubformath.mathhubservice.dtos.config;

public class SubjectDto {

    private Long id;

    private String subjectName;

    private GradeDto subjectGrade;


    public Long getId() {return id; }

    public void setId(Long id) {this.id = id;}


    public String getSubjectName() {return subjectName;}

    public void setSubjectName(String subjectName) {this.subjectName = subjectName;}


    public GradeDto getSubjectGrade() {return subjectGrade;}

    public void setSubjectGrade(GradeDto subjectGrade) {this.subjectGrade = subjectGrade;}
}
