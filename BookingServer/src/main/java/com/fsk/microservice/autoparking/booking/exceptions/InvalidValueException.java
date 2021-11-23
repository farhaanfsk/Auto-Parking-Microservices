package com.fsk.microservice.autoparking.booking.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String msg){
        super(msg);
    }
}
