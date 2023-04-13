package com.lv.conf.exceptions;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class ConferenceException extends ResourceNotFoundException {

    public ConferenceException(String message) {
        super(message);
    }

    public ConferenceException(String message, Throwable cause) {
        super(message, cause instanceof ConferenceException && cause.getCause() != null ? cause.getCause() : cause);
    }

}
