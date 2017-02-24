package com.ehealth.doctors.controller;

import com.ehealth.doctors.exception.ValidationFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationFormatException.class)
    protected ResponseEntity<Object> handleConflict(ValidationFormatException ex, HttpServletRequest request) throws JsonProcessingException {
        final ErrorObject errorObject = new ErrorObject(ex, request.getRequestURL().toString(), ex.data);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("X-Status-Reason", "Validation failed");

        return new ResponseEntity<>(errorObject, httpHeaders, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, HttpServletRequest request) throws JsonProcessingException {
        final ErrorObject errorObject = new ErrorObject(ex, request.getRequestURL().toString());

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>(errorObject, httpHeaders, BAD_REQUEST);
    }

    class ErrorObject {
        public final String type;
        public final List<Object> error = new ArrayList<>();
        public final String url;

        public ErrorObject(Exception ex, String url) {
            this.type = ex.getClass().getSimpleName();
            this.error.add(ex.getLocalizedMessage());
            this.url = url;
        }

        public ErrorObject(Exception ex, String url, List<ValidationFormatException.ValidationExplanation> data) {
            this.type = ex.getClass().getSimpleName();
            this.error.addAll(data);
            this.url = url;
        }
    }
}
