package com.hubformath.mathhubservice.services.sis;

import java.util.List;

import com.hubformath.mathhubservice.models.sis.Parent;

public interface IParentService {
    List<Parent> getAllParents();

    Parent createParent(Parent parent);

    Parent getParentById(Long id);

    Parent updateParent(Long id, Parent parent);

    void deleteParent(Long id);
}
