package com.elpidoroun.bookstoremanagement.exception;

import com.elpidoroun.bookstoremanagement.controller.MainController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static String INCORRECT_REQUEST = "Error in request";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new MainController.ErrorResponse(ex.getMessage(), INCORRECT_REQUEST));
    }

}
