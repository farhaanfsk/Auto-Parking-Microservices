package com.fsk.microservice.autoparking.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String msg){
        super(msg);
    }
}
