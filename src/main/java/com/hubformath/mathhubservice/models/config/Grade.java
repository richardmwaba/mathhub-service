package com.hubformath.mathhubservice.models.config;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String gradeName;

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
                && Objects.equals(this.createdAt, grade.createdAt)
                && Objects.equals(this.updatedAt, grade.updatedAt);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.gradeName, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString(){
        return "Grade{" + "id=" + this.id + ", gradeName=" + this.gradeName + "'" + ", createdAt='"
                + this.createdAt + ", updatedAt='" + this.updatedAt + "}";
    }
}
