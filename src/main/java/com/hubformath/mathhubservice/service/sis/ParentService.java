package com.hubformath.mathhubservice.service.sis;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.repository.sis.ParentRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    private final String notFoundItemName;
    
    public ParentService(final ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
        this.notFoundItemName = "parent";
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Parent getParentById(final Long id) {
        Optional<Parent> parent = parentRepository.findById(id);

        if(parent.isPresent()){
            return parent.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    public Parent createParent(final Parent parentRequest) {
        return parentRepository.save(parentRequest);
    }

    public Parent updateParent(final Long id, final Parent parentRequest) {
        return parentRepository.findById(id) 
                .map(parent -> {
                    parent.setFirstName(parentRequest.getFirstName());
                    parent.setMiddleName(parentRequest.getMiddleName());
                    parent.setLastName(parentRequest.getLastName());
                    parent.setEmail(parentRequest.getEmail());
                    parent.setStudents(parentRequest.getStudents());
                    parent.setAddresses(parentRequest.getAddresses());
                    parent.setPhoneNumbers(parentRequest.getPhoneNumbers());
                    return parentRepository.save(parent);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteParent(final Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        parentRepository.delete(parent);
    }
}
