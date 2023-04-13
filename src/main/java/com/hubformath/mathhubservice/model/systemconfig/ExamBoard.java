package com.hubformath.mathhubservice.model.systemconfig;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "exam_board")
public class ExamBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID examBoardId;

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

    public UUID getExamBoardId() {return this.examBoardId;}

    public void setExamBoardId(UUID examBoardId) {this.examBoardId = examBoardId;}

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
        if (this == o) return true;
        if (!(o instanceof ExamBoard examBoard)) return false;
        return Objects.equals(getExamBoardId(), examBoard.getExamBoardId())
                && Objects.equals(getExamBoardName(), examBoard.getExamBoardName())
                && Objects.equals(getExamBoardDescription(), examBoard.getExamBoardDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExamBoardId(), getExamBoardName(), getExamBoardDescription());
    }

    @Override
    public String toString() {
        return "ExamBoard{" +
                "examBoardId=" + examBoardId +
                ", examBoardName='" + examBoardName + '\'' +
                ", examBoardDescription='" + examBoardDescription + '\'' +
                '}';
    }
}
