package com.foodway.api.handler.exceptions;

public class EstablishmentNotFoundException extends RuntimeException{
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Establishment not found";
    public EstablishmentNotFoundException(String message) {
        super(message);
    }
}
