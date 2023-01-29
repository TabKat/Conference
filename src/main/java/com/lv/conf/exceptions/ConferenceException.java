package com.lv.conf.exceptions;

public class ConferenceException extends RuntimeException {

    public ConferenceException(String message) {
        super(message);
    }

    public ConferenceException(String message, Throwable cause) {
        super(message, cause instanceof ConferenceException && cause.getCause() != null? cause.getCause(): cause);
    }

}
