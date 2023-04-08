package com.lv.conf.exceptions;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class SitException extends ResourceNotFoundException {

    public SitException(String message) {
        super(message);
    }

    public SitException(String message, Throwable cause) {
        super(message, cause instanceof SitException && cause.getCause() != null ? cause.getCause() : cause);
    }
}

