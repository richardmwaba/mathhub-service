package com.hubformath.mathhubservice.services.sis.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.models.sis.Parent;
import com.hubformath.mathhubservice.repositories.sis.ParentRepository;
import com.hubformath.mathhubservice.services.sis.IParentService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@Service
public class ParentServiceImpl implements IParentService {
    private final ParentRepository parentRepository;
    private final String notFoundItemName;
    
    public ParentServiceImpl(ParentRepository parentRepository) {
        super();
        this.parentRepository = parentRepository;
        this.notFoundItemName = "parent";
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParentById(Long id) {
        Optional<Parent> parent = parentRepository.findById(id);

        if(parent.isPresent()){
            return parent.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Parent createParent(Parent parentRequest) {
        return parentRepository.save(parentRequest);
    }

    @Override
    public Parent updateParent(Long id, Parent parentRequest) {
        return parentRepository.findById(id) 
                .map(parent -> {
                    parent.setFirstName(parentRequest.getFirstName());
                    parent.setMiddleName(parentRequest.getMiddleName());
                    parent.setLastName(parentRequest.getLastName());
                    parent.setEmail(parentRequest.getEmail());
                    parent.setStudent(parentRequest.getStudent());
                    parent.setAddresses(parentRequest.getAddresses());
                    parent.setPhoneNumbers(parentRequest.getPhoneNumbers());
                    return parentRepository.save(parent);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteParent(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        parentRepository.delete(parent);
    }
}
