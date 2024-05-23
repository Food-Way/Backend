package com.foodway.api.handler.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public static final String CODE = "404";
    public static final String DESCRIPTION = "Comment not found";
    public CommentNotFoundException(String message) {
        super(message);
    }
}
