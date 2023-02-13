package com.hubformath.mathhubservice.service.systemconfig.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;
import com.hubformath.mathhubservice.repository.systemconfig.SessionTypeRepository;
import com.hubformath.mathhubservice.service.systemconfig.ISessionTypeService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

@Service
public class SessionTypeServiceImpl implements ISessionTypeService{
    private final SessionTypeRepository sessionTypeRepository;
    private final String notFoundItemName;
    
    public SessionTypeServiceImpl(SessionTypeRepository sessionTypeRepository) {
        super();
        this.sessionTypeRepository = sessionTypeRepository;
        this.notFoundItemName = "session type";
    }

    @Override
    public List<SessionType> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
    }

    @Override
    public SessionType getSessionTypeById(Long id) {
        Optional<SessionType> sessionType = sessionTypeRepository.findById(id);

        if(sessionType.isPresent()){
            return sessionType.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public SessionType createSessionType(SessionType sessionTypeRequest) {
        return sessionTypeRepository.save(sessionTypeRequest);
    }

    @Override
    public SessionType updateSessionType(Long id, SessionType sessionTypeRequest) {
        return sessionTypeRepository.findById(id) 
                .map(sessionType -> {
                    sessionType.setTypeName(sessionTypeRequest.getTypeName());
                    sessionType.setTypeDescription(sessionTypeRequest.getTypeDescription());
                    return sessionTypeRepository.save(sessionType);
                }) 
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteSessionType(Long id) {
        SessionType sessionType = sessionTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        sessionTypeRepository.delete(sessionType);
    }
}
