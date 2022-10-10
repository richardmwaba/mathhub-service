package com.hubformath.mathhubservice.utils.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id, String itemName) {
        super(String.format("Could not find %s %s", itemName, id) );
    }
}
