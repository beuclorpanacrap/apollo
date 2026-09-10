package com.apollo.exception;

public class InvalidAccessGrantException extends RuntimeException {
    public InvalidAccessGrantException(String message) {
        super(message);
    }
}
