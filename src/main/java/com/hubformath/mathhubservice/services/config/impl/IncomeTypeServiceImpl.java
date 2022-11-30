package com.hubformath.mathhubservice.services.config.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.config.IncomeType;
import com.hubformath.mathhubservice.repositories.config.IncomeTypeRepository;
import com.hubformath.mathhubservice.services.config.IIncomeTypeService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class IncomeTypeServiceImpl implements IIncomeTypeService{
    private final IncomeTypeRepository incomeTypeRepository;
    private final String notFoundItemName;
    
    public IncomeTypeServiceImpl(IncomeTypeRepository incomeTypeRepository) {
        super();
        this.incomeTypeRepository = incomeTypeRepository;
        this.notFoundItemName = "income type";
    }

    @Override
    public List<IncomeType> getAllIncomeTypes() {
        return incomeTypeRepository.findAll();
    }

    @Override
    public IncomeType getIncomeTypeById(Long id) {
        Optional<IncomeType> incomeType = incomeTypeRepository.findById(id);

        if(incomeType.isPresent()){
            return incomeType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public IncomeType createIncomeType(IncomeType incomeTypeRequest) {
        return incomeTypeRepository.save(incomeTypeRequest);
    }

    @Override
    public IncomeType updateIncomeType(Long id, IncomeType incomeTypeRequest) {
        return incomeTypeRepository.findById(id) 
                .map(incomeType -> {
                    incomeType.setTypeName(incomeTypeRequest.getTypeName());
                    incomeType.setTypeDescription(incomeTypeRequest.getTypeDescription());
                    return incomeTypeRepository.save(incomeType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteIncomeType(Long id) {
        IncomeType incomeType = incomeTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        incomeTypeRepository.delete(incomeType);
    }
}
