package com.hubformath.mathhubservice.services.config.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.config.EquityType;
import com.hubformath.mathhubservice.repositories.config.EquityTypeRepository;
import com.hubformath.mathhubservice.services.config.IEquityTypeService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class EquityTypeServiceImpl implements IEquityTypeService{
    private final EquityTypeRepository equityTypeRepository;
    private final String notFoundItemName;
    
    public EquityTypeServiceImpl(EquityTypeRepository equityTypeRepository) {
        super();
        this.equityTypeRepository = equityTypeRepository;
        this.notFoundItemName = "equity type";
    }

    @Override
    public List<EquityType> getAllEquityTypes() {
        return equityTypeRepository.findAll();
    }

    @Override
    public EquityType getEquityTypeById(Long id) {
        Optional<EquityType> equityType = equityTypeRepository.findById(id);

        if(equityType.isPresent()){
            return equityType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public EquityType createEquityType(EquityType equityTypeRequest) {
        return equityTypeRepository.save(equityTypeRequest);
    }

    @Override
    public EquityType updateEquityType(Long id, EquityType equityTypeRequest) {
        return equityTypeRepository.findById(id) 
                .map(equityType -> {
                    equityType.setTypeName(equityTypeRequest.getTypeName());
                    equityType.setTypeDescription(equityTypeRequest.getTypeDescription());
                    return equityTypeRepository.save(equityType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteEquityType(Long id) {
        EquityType equityType = equityTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        equityTypeRepository.delete(equityType);
    }
}
