package com.hubformath.mathhubservice.model.systemconfig;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String syllabusName;
    
    private String syllabusDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Syllabus(){}


    public Syllabus(String syllabusName, String syllabusDescription) {
        this.syllabusName = syllabusName;
        this.syllabusDescription = syllabusDescription;
    }


    public Long getId() {return this.id;}


    public String getSyllabusName() {return this.syllabusName;}

    public void setSyllabusName(String syllabusName) {this.syllabusName = syllabusName;}


    public String getSyllabusDescription() {return this.syllabusDescription;}

    public void setSyllabusDescription(String syllabusDescription) {this.syllabusDescription = syllabusDescription;}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Syllabus))
            return false;
        Syllabus syllabus = (Syllabus) o;
        return Objects.equals(this.id, syllabus.id) && Objects.equals(this.syllabusName, syllabus.syllabusName)
                && Objects.equals(this.syllabusDescription, syllabus.syllabusDescription)
                && Objects.equals(this.createdAt, syllabus.createdAt)
                && Objects.equals(this.updatedAt, syllabus.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.syllabusName, this.syllabusDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "Syllabus{" + "id=" + this.id + ", syllabusName='" + this.syllabusName + "'" + ", syllabusDescription='"
                + this.syllabusDescription + ", asset=" + ", createdAt=" + this.createdAt
                + ", updatedAt=" + this.updatedAt + "}";
    }
}
