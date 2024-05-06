package com.elpidoroun.bookstoremanagement.exception;

public class EntityNotFoundException extends RuntimeException {

    private final String errorType;

    public EntityNotFoundException(String message){
        super(message);
        this.errorType = "Entity not found";
    }
}
