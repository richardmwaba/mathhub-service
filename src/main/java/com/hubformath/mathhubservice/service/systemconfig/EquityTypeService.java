package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.repository.systemconfig.EquityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquityTypeService {

    private final EquityTypeRepository equityTypeRepository;

    public EquityTypeService(final EquityTypeRepository equityTypeRepository) {
        this.equityTypeRepository = equityTypeRepository;
    }

    public List<EquityType> getAllEquityTypes() {
        return equityTypeRepository.findAll();
    }

    public EquityType getEquityTypeById(Long id) {
        return equityTypeRepository.findById(id).orElseThrow();
    }

    public EquityType createEquityType(EquityType equityTypeRequest) {
        return equityTypeRepository.save(equityTypeRequest);
    }

    public EquityType updateEquityType(Long id, EquityType equityTypeRequest) {
        return equityTypeRepository.findById(id) 
                .map(equityType -> {
                    equityType.setTypeName(equityTypeRequest.getTypeName());
                    equityType.setTypeDescription(equityTypeRequest.getTypeDescription());
                    return equityTypeRepository.save(equityType);
                }) 
                .orElseThrow();
    }

    public void deleteEquityType(Long id) {
        EquityType equityType = equityTypeRepository.findById(id)
                .orElseThrow();

        equityTypeRepository.delete(equityType);
    }
}
