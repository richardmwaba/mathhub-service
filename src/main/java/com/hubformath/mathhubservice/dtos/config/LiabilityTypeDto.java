package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.LiabilityDto;

public class LiabilityTypeDto {
    private Long id;

    private String name;

    private String description;

    private LiabilityDto liability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return name;
    }

    public void setTypeName(String name) {
        this.name = name;
    }

    public String getTypeDescription() {
        return description;
    }

    public void setTypeDescription(String description) {
        this.description = description;
    }

    public LiabilityDto getLiability() {
        return liability;
    }

    public void setLiability(LiabilityDto liability) {
        this.liability = liability;
    }
}
