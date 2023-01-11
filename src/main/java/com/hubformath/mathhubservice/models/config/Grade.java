package com.hubformath.mathhubservice.models.config;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String gradeName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectGrade")
    private List<Subject> subjects;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private  LocalDateTime updatedAt;

    public Grade(){}

    public Grade(String gradeName){
        this.gradeName = gradeName;
    }


    public Long getId() {
        return this.id;
    }

    public String getGradeName() {return this.gradeName;}

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<Subject> getSubjects() {return subjects; }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return  true;
        if(!(o instanceof Grade))
            return  false;
        Grade grade = (Grade) o;
        return Objects.equals(this.id, grade.id)
                && Objects.equals(this.gradeName, grade.gradeName)
                && Objects.equals(this.subjects, grade.subjects)
                && Objects.equals(this.createdAt, grade.createdAt)
                && Objects.equals(this.updatedAt, grade.updatedAt);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.gradeName, this.subjects, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString(){
        return "Grade{" + "id=" + this.id + ", gradeName=" + this.gradeName + "subject=" + this.subjects + ", createdAt="
                + this.createdAt + ", updatedAt=" + this.updatedAt + "}";
    }


}
