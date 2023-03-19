package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.repository.sis.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(final ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Parent getParentById(final Long id) {
        return parentRepository.findById(id).orElseThrow();
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
                .orElseThrow();
    }

    public void deleteParent(final Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow();

        parentRepository.delete(parent);
    }
}
