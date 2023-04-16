package com.hubformath.mathhubservice.dto.systemconfig;

import java.util.UUID;

public class SyllabusDto {

    private UUID syllabusId;

    private String syllabusName;

    private String syllabusDescription;

    public UUID getSyllabusId() {return syllabusId;}

    public void setSyllabusId(UUID syllabusId) {this.syllabusId = syllabusId;}

    public String getSyllabusName() {return syllabusName;}

    public void setSyllabusName(String syllabusName) {this.syllabusName = syllabusName;}

    public String getSyllabusDescription() {return syllabusDescription;}

    public void setSyllabusDescription(String syllabusDescription) {this.syllabusDescription = syllabusDescription;}
}
