package com.fsk.microservice.autoparking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fsk.microservice.autoparking.entity.ParkingResponse;
import com.fsk.microservice.autoparking.exceptions.InvalidValueException;

@ControllerAdvice
public class ParkingExceptionHandller {

	@ExceptionHandler
	public ResponseEntity<ParkingResponse> badRequest(InvalidValueException ex) {
		return new ResponseEntity<ParkingResponse>(new ParkingResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
