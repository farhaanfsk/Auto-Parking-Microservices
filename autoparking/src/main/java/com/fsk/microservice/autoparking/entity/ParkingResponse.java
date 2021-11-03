package com.fsk.microservice.autoparking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParkingResponse {
	private int statusCode;
	private String message;
	
	public ParkingResponse(){
		
	}
}
