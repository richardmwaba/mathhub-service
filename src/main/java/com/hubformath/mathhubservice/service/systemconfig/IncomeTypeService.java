package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.repository.systemconfig.IncomeTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeTypeService {

    private final IncomeTypeRepository incomeTypeRepository;

    public IncomeTypeService(IncomeTypeRepository incomeTypeRepository) {
        this.incomeTypeRepository = incomeTypeRepository;
    }

    public List<IncomeType> getAllIncomeTypes() {
        return incomeTypeRepository.findAll();
    }

    public IncomeType getIncomeTypeById(Long id) {
        return incomeTypeRepository.findById(id).orElseThrow();
    }

    public IncomeType createIncomeType(IncomeType incomeTypeRequest) {
        return incomeTypeRepository.save(incomeTypeRequest);
    }

    public IncomeType updateIncomeType(Long id, IncomeType incomeTypeRequest) {
        return incomeTypeRepository.findById(id) 
                .map(incomeType -> {
                    incomeType.setTypeName(incomeTypeRequest.getTypeName());
                    incomeType.setTypeDescription(incomeTypeRequest.getTypeDescription());
                    return incomeTypeRepository.save(incomeType);
                }) 
                .orElseThrow();
    }

    public void deleteIncomeType(Long id) {
        IncomeType incomeType = incomeTypeRepository.findById(id)
                .orElseThrow();

        incomeTypeRepository.delete(incomeType);
    }
}
