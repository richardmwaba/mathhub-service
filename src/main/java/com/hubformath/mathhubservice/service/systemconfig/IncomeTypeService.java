package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.repository.systemconfig.IncomeTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeTypeService {

    private final IncomeTypeRepository incomeTypeRepository;

    public IncomeTypeService(IncomeTypeRepository incomeTypeRepository) {
        this.incomeTypeRepository = incomeTypeRepository;
    }

    public List<IncomeType> getAllIncomeTypes() {
        return incomeTypeRepository.findAll();
    }

    public IncomeType getIncomeTypeById(String incomeTypeId) {
        return incomeTypeRepository.findById(incomeTypeId).orElseThrow();
    }

    public IncomeType createIncomeType(IncomeType incomeTypeRequest) {
        return incomeTypeRepository.save(incomeTypeRequest);
    }

    public IncomeType updateIncomeType(String incomeTypeId, IncomeType incomeTypeRequest) {
        return incomeTypeRepository.findById(incomeTypeId)
                                   .map(incomeType -> {
                                       Optional.ofNullable(incomeTypeRequest.getName())
                                               .ifPresent(incomeType::setName);
                                       Optional.ofNullable(incomeTypeRequest.getDescription())
                                               .ifPresent(incomeType::setDescription);
                                       return incomeTypeRepository.save(incomeType);
                                   })
                                   .orElseThrow();
    }

    public void deleteIncomeType(String incomeTypeId) {
        IncomeType incomeType = incomeTypeRepository.findById(incomeTypeId)
                                                    .orElseThrow();

        incomeTypeRepository.delete(incomeType);
    }
}
