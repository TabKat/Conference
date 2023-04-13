package com.lv.conf.exceptions;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class RoomException extends ResourceNotFoundException {

    public RoomException(String message) {
        super(message);
    }

    public RoomException(String message, Throwable cause) {
        super(message, cause instanceof RoomException && cause.getCause() != null ? cause.getCause() : cause);
    }

}