package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class ExamBoardDto {

    private UUID examBoardId;

    private String examBoardName;

    private String examBoardDescription;

    public UUID getExamBoardId() {
        return examBoardId;}

    public void setExamBoardId(UUID examBoardId) {
        this.examBoardId = examBoardId;}

    public String getExamBoardName() {
        return examBoardName;}

    public void setExamBoardName(String examBoardName) {
        this.examBoardName = examBoardName;}

    public String getExamBoardDescription() {
        return examBoardDescription;}

    public void setExamBoardDescription(String examBoardDescription) {
        this.examBoardDescription = examBoardDescription;}
}
