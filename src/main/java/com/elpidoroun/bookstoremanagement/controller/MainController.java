package com.elpidoroun.bookstoremanagement.controller;

import com.elpidoroun.bookstoremanagement.controller.command.AbstractRequest;
import com.elpidoroun.bookstoremanagement.controller.command.Command;
import com.elpidoroun.bookstoremanagement.exception.DatabaseOperationException;
import com.elpidoroun.bookstoremanagement.exception.EntityNotFoundException;
import com.google.common.collect.ImmutableMap;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.elpidoroun.bookstoremanagement.utility.StringUtils.requireNonBlank;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MainController {

    private static final Map<Class<?>, HttpStatus> exceptionStatuses = new ImmutableMap.Builder<Class<?>, HttpStatus>()
            .put(DatabaseOperationException.class, INTERNAL_SERVER_ERROR)
            .put(EntityNotFoundException.class, NOT_FOUND)
            .build();

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request){
        return execute(command, request, ResponseEntity::ok);
    }

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request,
            Function<ResponseT, ResponseEntity<?>> responseBuilder){
        try{

            MDC.put("operation", command.getOperation());

            return responseBuilder.apply(command.execute(request));
        } catch (Exception exception){
            return ResponseEntity.status(httpStatusByException(exception))
                    .body(new ErrorResponse(exception.getMessage(), getErrorType(exception)));
        }
    }

    private String getErrorType(Exception exception) {
        try {
            Field errorTypeField = exception.getClass().getDeclaredField("errorType");
            errorTypeField.setAccessible(true);
            return (String) errorTypeField.get(exception);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "Unknown error type";
        }
    }

    private HttpStatus httpStatusByException(Exception exception){
        return Optional.ofNullable(exceptionStatuses.get(exception.getClass())).orElse(INTERNAL_SERVER_ERROR);
    }

    public record ErrorResponse(String message, String errorType) {
        public ErrorResponse(String message, String errorType) {
            this.message = requireNonBlank(message, "Message is missing");
            this.errorType = requireNonBlank(errorType, "ErrorType is missing");
        }
    }

}
