package com.hubformath.mathhubservice.dtos.config;

import com.hubformath.mathhubservice.dtos.ops.cashbook.EquityDto;

public class EquityTypeDto {
    private Long id;

    private String name;

    private String description;

    private EquityDto equity;

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

    public EquityDto getEquity() {
        return equity;
    }

    public void setEquity(EquityDto equity) {
        this.equity = equity;
    }
}
