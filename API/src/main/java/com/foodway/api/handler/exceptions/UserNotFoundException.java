package com.foodway.api.handler.exceptions;

public class UserNotFoundException extends RuntimeException{
    public static final String CODE = "404";
    public static final String DESCRIPTION = "User not found";

    public UserNotFoundException(String message) {
        super(message);
    }
}
