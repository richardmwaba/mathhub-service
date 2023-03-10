package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.repository.systemconfig.LiabilityTypeRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class LiabilityTypeService {
    private final LiabilityTypeRepository liabilityTypeRepository;
    private final String notFoundItemName;
    
    public LiabilityTypeService(LiabilityTypeRepository liabilityTypeRepository) {
        super();
        this.liabilityTypeRepository = liabilityTypeRepository;
        this.notFoundItemName = "liability type";
    }

    public List<LiabilityType> getAllLiabilityTypes() {
        return liabilityTypeRepository.findAll();
    }

    public LiabilityType getLiabilityTypeById(Long id) {
        Optional<LiabilityType> liabilityType = liabilityTypeRepository.findById(id);

        if(liabilityType.isPresent()){
            return liabilityType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public LiabilityType createLiabilityType(LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.save(liabilityTypeRequest);
    }

    public LiabilityType updateLiabilityType(Long id, LiabilityType liabilityTypeRequest) {
        return liabilityTypeRepository.findById(id) 
                .map(liabilityType -> {
                    liabilityType.setTypeName(liabilityTypeRequest.getTypeName());
                    liabilityType.setTypeDescription(liabilityTypeRequest.getTypeDescription());
                    return liabilityTypeRepository.save(liabilityType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteLiabilityType(Long id) {
        LiabilityType liabilityType = liabilityTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        liabilityTypeRepository.delete(liabilityType);
    }
}
