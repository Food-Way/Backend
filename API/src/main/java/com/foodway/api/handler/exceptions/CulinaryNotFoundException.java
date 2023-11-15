package com.foodway.api.handler.exceptions;

public class CulinaryNotFoundException extends RuntimeException {
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Culianry not found";
    public CulinaryNotFoundException(String message) {
        super(message);
    }
}
