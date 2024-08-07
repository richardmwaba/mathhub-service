package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.repository.systemconfig.EquityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquityTypeService {

    private final EquityTypeRepository equityTypeRepository;

    public EquityTypeService(final EquityTypeRepository equityTypeRepository) {
        this.equityTypeRepository = equityTypeRepository;
    }

    public List<EquityType> getAllEquityTypes() {
        return equityTypeRepository.findAll();
    }

    public EquityType getEquityTypeById(String equityTypeId) {
        return equityTypeRepository.findById(equityTypeId).orElseThrow();
    }

    public EquityType createEquityType(EquityType equityTypeRequest) {
        return equityTypeRepository.save(equityTypeRequest);
    }

    public EquityType updateEquityType(String equityTypeId, EquityType equityTypeRequest) {
        return equityTypeRepository.findById(equityTypeId)
                                   .map(equityType -> {
                                       Optional.ofNullable(equityTypeRequest.getName())
                                               .ifPresent(equityType::setName);
                                       Optional.ofNullable(equityTypeRequest.getDescription())
                                               .ifPresent(equityType::setDescription);
                                       return equityTypeRepository.save(equityType);
                                   })
                                   .orElseThrow();
    }

    public void deleteEquityType(String equityTypeId) {
        EquityType equityType = equityTypeRepository.findById(equityTypeId)
                                                    .orElseThrow();

        equityTypeRepository.delete(equityType);
    }
}
