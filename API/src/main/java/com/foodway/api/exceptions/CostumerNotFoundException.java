package com.foodway.api.exceptions;

public class CostumerNotFoundException extends RuntimeException {
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Costumer not found";
    public CostumerNotFoundException(String message) {
        super(message);
    }
}
