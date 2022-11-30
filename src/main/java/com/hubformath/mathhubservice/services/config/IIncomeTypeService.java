package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.IncomeType;

public interface IIncomeTypeService {
    public List<IncomeType> getAllIncomeTypes();

    public IncomeType createIncomeType(IncomeType incomeType);

    public IncomeType getIncomeTypeById(Long id);

    public IncomeType updateIncomeType(Long id, IncomeType incomeType);

    public void deleteIncomeType(Long id);
}
