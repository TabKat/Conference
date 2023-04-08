package com.lv.conf.exceptions;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class ParticipantException extends ResourceNotFoundException {

    public ParticipantException(String message) {
        super(message);
    }

    public ParticipantException(String message, Throwable cause) {
        super(message, cause instanceof ParticipantException && cause.getCause() != null? cause.getCause(): cause);
    }

}
