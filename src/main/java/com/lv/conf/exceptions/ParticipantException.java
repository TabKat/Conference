package com.lv.conf.exceptions;

public class ParticipantException extends RuntimeException {

    public ParticipantException(String message) {
        super(message);
    }

    public ParticipantException(String message, Throwable cause) {
        super(message, cause instanceof ParticipantException && cause.getCause() != null? cause.getCause(): cause);
    }

}
