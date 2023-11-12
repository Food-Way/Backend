package com.foodway.api.exceptions;

public class RateNotFoundException extends RuntimeException{

    public static final String CODE = "404";
    public static final String DESCRIPTION = "Rate not found";

    public RateNotFoundException(String message) {
        super(message);
    }

}
