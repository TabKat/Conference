package com.lv.conf.exceptions;

public class RoomException extends RuntimeException {

    public RoomException(String message) {
        super(message);
    }

    public RoomException(String message, Throwable cause) {
        super(message, cause instanceof RoomException && cause.getCause() != null? cause.getCause(): cause);
    }

}