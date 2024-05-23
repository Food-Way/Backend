package com.foodway.api.handler.exceptions;

public class UpvoteNotFoundException extends RuntimeException{
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Upvote not found";

    public UpvoteNotFoundException(String message) {
        super(message);
    }
}
