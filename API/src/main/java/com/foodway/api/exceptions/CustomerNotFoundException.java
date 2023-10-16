package com.foodway.api.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Customer not found";
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
