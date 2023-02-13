package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.SessionType;

public interface ISessionTypeService {
    public List<SessionType> getAllSessionTypes();

    public SessionType createSessionType(SessionType sessionType);

    public SessionType getSessionTypeById(Long id);

    public SessionType updateSessionType(Long id, SessionType sessionType);

    public void deleteSessionType(Long id);
}
