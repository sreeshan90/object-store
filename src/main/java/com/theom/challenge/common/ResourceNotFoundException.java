package com.theom.challenge.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

/**
 * Exception class when the user  is not found in DB
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String id) {
        super("Could not find User " + id);
    }

}