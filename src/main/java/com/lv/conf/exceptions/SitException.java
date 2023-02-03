package com.lv.conf.exceptions;

public class SitException extends RuntimeException {

    public SitException(String message) {
        super(message);
    }

    public SitException(String message, Throwable cause) {
        super(message, cause instanceof SitException && cause.getCause() != null ? cause.getCause() : cause);
    }
}

