package com.hubformath.mathhubservice.service.systemconfig.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.repository.systemconfig.LiabilityTypeRepository;
import com.hubformath.mathhubservice.service.systemconfig.ILiabilityTypeService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class LiabilityTypeServiceImpl implements ILiabilityTypeService{
    private final LiabilityTypeRepository liabilityTypeRepository;
    private final String notFoundItemName;
    
    public LiabilityTypeServiceImpl(LiabilityTypeRepository liabilityTypeRepository) {
        super();
        this.liabilityTypeRepository = liabilityTypeRepository;
        this.notFoundItemName = "liability type";
    }

    @Override
    public List<LiabilityType> getAllLiabilityTypes() {
        return liabilityTypeRepository.findAll();
    }

    @Override
    public LiabilityType getLiabilityTypeById(Long id) {
        Optional<LiabilityType> liabilityType = liabilityTypeRepository.findById(id);

        if(liabilityType.isPresent()){
            return liabilityType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public LiabilityType createLiabilityType(LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.save(liabilityTypeRequest);
    }

    @Override
    public LiabilityType updateLiabilityType(Long id, LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.findById(id) 
                .map(liabilityType -> {
                    liabilityType.setTypeName(liabilityTypeRequest.getTypeName());
                    liabilityType.setTypeDescription(liabilityTypeRequest.getTypeDescription());
                    return liabilityTypeRepository.save(liabilityType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteLiabilityType(Long id) {
        LiabilityType liabilityType = liabilityTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        liabilityTypeRepository.delete(liabilityType);
    }
}
