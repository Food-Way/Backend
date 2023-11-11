package com.foodway.api.exceptions;

public class CulinaryNotFoundException extends RuntimeException {
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Culinary not found";

    public CulinaryNotFoundException(String message) {
        super(message);
    }
}
