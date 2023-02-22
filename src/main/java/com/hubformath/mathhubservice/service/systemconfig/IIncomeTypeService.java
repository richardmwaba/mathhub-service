package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.IncomeType;

public interface IIncomeTypeService {
   List<IncomeType> getAllIncomeTypes();

   IncomeType createIncomeType(IncomeType incomeType);

   IncomeType getIncomeTypeById(Long id);

   IncomeType updateIncomeType(Long id, IncomeType incomeType);

   void deleteIncomeType(Long id);
}
