package com.hubformath.mathhubservice.controller.util;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;

import java.util.List;

public class CollectionModelUtils {

    private CollectionModelUtils() {
    }

    public static <T> CollectionModel<EmbeddedWrapper> getEmptyEmbeddedCollectionModel(Class<T> clazz, Link... links) {
        EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
        EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(clazz);
        return CollectionModel.of(List.of(wrapper), links);
    }
}
