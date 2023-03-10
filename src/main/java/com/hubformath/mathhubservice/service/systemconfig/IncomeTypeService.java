package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.repository.systemconfig.IncomeTypeRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class IncomeTypeService {
    private final IncomeTypeRepository incomeTypeRepository;
    private final String notFoundItemName;
    
    public IncomeTypeService(IncomeTypeRepository incomeTypeRepository) {
        super();
        this.incomeTypeRepository = incomeTypeRepository;
        this.notFoundItemName = "income type item";
    }

    public List<IncomeType> getAllIncomeTypes() {
        return incomeTypeRepository.findAll();
    }

    public IncomeType getIncomeTypeById(Long id) {
        Optional<IncomeType> incomeType = incomeTypeRepository.findById(id);

        if(incomeType.isPresent()){
            return incomeType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteIncomeType(Long id) {
        IncomeType incomeType = incomeTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        incomeTypeRepository.delete(incomeType);
    }
}
