package com.example.assignment.utils.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(
            String message
    ) {
        super(message);
    }
}
