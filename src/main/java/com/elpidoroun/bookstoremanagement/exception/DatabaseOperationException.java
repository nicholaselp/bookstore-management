package com.elpidoroun.bookstoremanagement.exception;

public class DatabaseOperationException  extends RuntimeException{

    private final String errorType;

    public DatabaseOperationException(String message){
        super(message);
        this.errorType = "Database error";
    }
}
