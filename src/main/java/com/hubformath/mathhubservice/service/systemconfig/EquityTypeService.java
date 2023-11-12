package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.repository.systemconfig.EquityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquityTypeService {

    private final EquityTypeRepository equityTypeRepository;

    public EquityTypeService(final EquityTypeRepository equityTypeRepository) {
        this.equityTypeRepository = equityTypeRepository;
    }

    public List<EquityType> getAllEquityTypes() {
        return equityTypeRepository.findAll();
    }

    public EquityType getEquityTypeById(UUID equityTypeId) {
        return equityTypeRepository.findById(equityTypeId).orElseThrow();
    }

    public EquityType createEquityType(EquityType equityTypeRequest) {
        return equityTypeRepository.save(equityTypeRequest);
    }

    public EquityType updateEquityType(UUID equityTypeId, EquityType equityTypeRequest) {
        return equityTypeRepository.findById(equityTypeId)
                                   .map(equityType -> {
                                       Optional.ofNullable(equityTypeRequest.getTypeName())
                                               .ifPresent(equityType::setTypeName);
                                       Optional.ofNullable(equityTypeRequest.getTypeDescription())
                                               .ifPresent(equityType::setTypeDescription);
                                       return equityTypeRepository.save(equityType);
                                   })
                                   .orElseThrow();
    }

    public void deleteEquityType(UUID equityTypeId) {
        EquityType equityType = equityTypeRepository.findById(equityTypeId)
                                                    .orElseThrow();

        equityTypeRepository.delete(equityType);
    }
}
