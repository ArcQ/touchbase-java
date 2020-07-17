package com.kf.touchbase.exception;

public class NotYetImplementedException extends RuntimeException {
    public NotYetImplementedException() {
        super("Feature is not implemented yet");
    }
}
