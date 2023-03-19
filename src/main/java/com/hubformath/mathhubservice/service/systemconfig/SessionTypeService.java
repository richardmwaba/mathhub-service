package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;
import com.hubformath.mathhubservice.repository.systemconfig.SessionTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionTypeService {

    private final SessionTypeRepository sessionTypeRepository;

    public SessionTypeService(SessionTypeRepository sessionTypeRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
    }

    public List<SessionType> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
    }

    public SessionType getSessionTypeById(Long id) {
        return sessionTypeRepository.findById(id).orElseThrow();
    }

    public SessionType createSessionType(SessionType sessionTypeRequest) {
        return sessionTypeRepository.save(sessionTypeRequest);
    }

    public SessionType updateSessionType(Long id, SessionType sessionTypeRequest) {
        return sessionTypeRepository.findById(id) 
                .map(sessionType -> {
                    sessionType.setTypeName(sessionTypeRequest.getTypeName());
                    sessionType.setTypeDescription(sessionTypeRequest.getTypeDescription());
                    return sessionTypeRepository.save(sessionType);
                }) 
                .orElseThrow();
    }

    public void deleteSessionType(Long id) {
        SessionType sessionType = sessionTypeRepository.findById(id)
                .orElseThrow();

        sessionTypeRepository.delete(sessionType);
    }
}
