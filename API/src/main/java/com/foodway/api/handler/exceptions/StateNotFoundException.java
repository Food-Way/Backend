package com.foodway.api.handler.exceptions;

public class StateNotFoundException extends RuntimeException{
    public static final String CODE = "404";
    public static final String DESCRIPTION = "State not found!";
    public StateNotFoundException(String message) {
        super(message);
    }
}
