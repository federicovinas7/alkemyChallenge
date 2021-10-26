package com.alkemy.challenge.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolations(ConstraintViolationException ex, WebRequest request){

        List<String>errors = new ArrayList<>();
        for(ConstraintViolation violation : ex.getConstraintViolations())
            errors.add(violation.getRootBeanClass() + " " + violation.getMessage());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,errors,ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError,new HttpHeaders(),apiError.getStatus());
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex,WebRequest request){
        List<String>errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError = new ApiError(ex.getStatus(),errors,ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError,new HttpHeaders(),apiError.getStatus());
    }
}
