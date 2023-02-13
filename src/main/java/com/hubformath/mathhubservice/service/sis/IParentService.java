package com.hubformath.mathhubservice.service.sis;

import java.util.List;

import com.hubformath.mathhubservice.model.sis.Parent;

public interface IParentService {
    List<Parent> getAllParents();

    Parent createParent(Parent parent);

    Parent getParentById(Long id);

    Parent updateParent(Long id, Parent parent);

    void deleteParent(Long id);
}
