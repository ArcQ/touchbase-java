package com.kf.touchbase.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("Unauthorized to execute this action");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
