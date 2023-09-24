package com.foodway.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CostumerNotFoundException.class)
    public ProblemDetail costumerNotFound(CostumerNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create(""));
        problemDetail.setTitle("Costumer not found");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
}
