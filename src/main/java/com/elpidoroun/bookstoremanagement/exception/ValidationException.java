package com.elpidoroun.bookstoremanagement.exception;

public class ValidationException extends RuntimeException {

    private final String errorType;

    public ValidationException(String message){
        super(message);
        this.errorType = "Validation error occurred";
    }
}
