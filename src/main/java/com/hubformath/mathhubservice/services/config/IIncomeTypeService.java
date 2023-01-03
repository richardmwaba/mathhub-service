package com.hubformath.mathhubservice.services.config;

import java.util.List;

import com.hubformath.mathhubservice.models.config.IncomeType;

public interface IIncomeTypeService {
   List<IncomeType> getAllIncomeTypes();

   IncomeType createIncomeType(IncomeType incomeType);

   IncomeType getIncomeTypeById(Long id);

   IncomeType updateIncomeType(Long id, IncomeType incomeType);

   void deleteIncomeType(Long id);
}
