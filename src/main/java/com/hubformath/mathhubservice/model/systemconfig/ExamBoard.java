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
public class ExamBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String examBoardName;
    
    private String examBoardDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public ExamBoard(){}

    public ExamBoard(final String examBoardName, final String examBoardDescription) {
        this.examBoardName = examBoardName;
        this.examBoardDescription = examBoardDescription;
    }

    public Long getId() {return this.id;}

    public String getExamBoardName() {return this.examBoardName;}

    public void setExamBoardName(String examBoardName) {this.examBoardName = examBoardName;}

    public String getExamBoardDescription() {return this.examBoardDescription;}

    public void setExamBoardDescription(String examBoardDescription) {this.examBoardDescription = examBoardDescription;}

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
        if (!(o instanceof ExamBoard))
            return false;
        ExamBoard examBoard = (ExamBoard) o;
        return Objects.equals(this.id, examBoard.id) && Objects.equals(this.examBoardName, examBoard.examBoardName)
                && Objects.equals(this.examBoardDescription, examBoard.examBoardDescription)
                && Objects.equals(this.createdAt, examBoard.createdAt)
                && Objects.equals(this.updatedAt, examBoard.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.examBoardName, this.examBoardDescription, this.createdAt, this.updatedAt);
    }

    @Override
    public String toString() {
        return "ExamBoard{" + "id=" + this.id + ", examBoardName='" + this.examBoardName + "'" + ", examBoardDescription='"
                + this.examBoardDescription + ", createdAt=" + this.createdAt
                + ", updatedAt=" + this.updatedAt + "}";
    }
}
