package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.EquityType;

public interface IEquityTypeService {
    public List<EquityType> getAllEquityTypes();

    public EquityType createEquityType(EquityType equityType);

    public EquityType getEquityTypeById(Long id);

    public EquityType updateEquityType(Long id, EquityType equityType);

    public void deleteEquityType(Long id);
}
