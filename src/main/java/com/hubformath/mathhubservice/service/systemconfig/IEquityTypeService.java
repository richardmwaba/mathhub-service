package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;

public interface IEquityTypeService {
    public List<EquityType> getAllEquityTypes();

    public EquityType createEquityType(EquityType equityType);

    public EquityType getEquityTypeById(Long id);

    public EquityType updateEquityType(Long id, EquityType equityType);

    public void deleteEquityType(Long id);
}
