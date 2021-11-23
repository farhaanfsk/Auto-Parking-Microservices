package com.fsk.microservice.autoparking.cancellation.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String msg){
        super(msg);
    }
}
