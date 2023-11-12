package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;
import com.hubformath.mathhubservice.repository.systemconfig.SessionTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionTypeService {

    private final SessionTypeRepository sessionTypeRepository;

    public SessionTypeService(SessionTypeRepository sessionTypeRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
    }

    public List<SessionType> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
    }

    public SessionType getSessionTypeById(UUID sessionTypeId) {
        return sessionTypeRepository.findById(sessionTypeId).orElseThrow();
    }

    public SessionType createSessionType(SessionType sessionTypeRequest) {
        return sessionTypeRepository.save(sessionTypeRequest);
    }

    public SessionType updateSessionType(UUID sessionTypeId, SessionType sessionTypeRequest) {
        return sessionTypeRepository.findById(sessionTypeId)
                                    .map(sessionType -> {
                                        Optional.ofNullable(sessionTypeRequest.getTypeName())
                                                .ifPresent(sessionType::setTypeName);
                                        Optional.ofNullable(sessionTypeRequest.getTypeDescription())
                                                .ifPresent(sessionType::setTypeDescription);
                                        return sessionTypeRepository.save(sessionType);
                                    })
                                    .orElseThrow();
    }

    public void deleteSessionType(UUID sessionTypeId) {
        SessionType sessionType = sessionTypeRepository.findById(sessionTypeId)
                                                       .orElseThrow();

        sessionTypeRepository.delete(sessionType);
    }
}
