package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.repository.sis.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(final ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Parent getParentById(final UUID parentId) {
        return parentRepository.findById(parentId).orElseThrow();
    }

    public Parent createParent(final Parent parentRequest) {
        return parentRepository.save(parentRequest);
    }

    public Parent updateParent(final UUID parentId, final Parent parentRequest) {
        return parentRepository.findById(parentId)
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

    public void deleteParent(final UUID parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow();

        parentRepository.delete(parent);
    }
}
