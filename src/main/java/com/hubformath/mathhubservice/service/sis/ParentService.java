package com.hubformath.mathhubservice.service.sis;

import com.hubformath.mathhubservice.model.sis.Parent;
import com.hubformath.mathhubservice.repository.sis.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(final ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Parent getParentById(final String parentId) {
        return parentRepository.findById(parentId).orElseThrow();
    }

    public Parent createParent(final Parent parentRequest) {
        return parentRepository.save(parentRequest);
    }

    public Parent updateParent(final String parentId, final Parent parentRequest) {
        return parentRepository.findById(parentId)
                               .map(parent -> {
                                   Optional.ofNullable(parentRequest.getFirstName()).ifPresent(parent::setFirstName);
                                   Optional.ofNullable(parentRequest.getMiddleName()).ifPresent(parent::setMiddleName);
                                   Optional.ofNullable(parentRequest.getLastName()).ifPresent(parent::setLastName);
                                   Optional.ofNullable(parentRequest.getEmail()).ifPresent(parent::setEmail);
                                   Optional.ofNullable(parentRequest.getAddresses()).ifPresent(parent::setAddresses);
                                   Optional.ofNullable(parentRequest.getPhoneNumbers())
                                           .ifPresent(parent::setPhoneNumbers);
                                   return parentRepository.save(parent);
                               })
                               .orElseThrow();
    }

    public void deleteParent(final String parentId) {
        Parent parent = parentRepository.findById(parentId)
                                        .orElseThrow();

        parentRepository.delete(parent);
    }
}
