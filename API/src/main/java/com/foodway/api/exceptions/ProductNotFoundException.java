package com.foodway.api.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Product not found";
    public ProductNotFoundException(String message) {
        super(message);
    }
}
