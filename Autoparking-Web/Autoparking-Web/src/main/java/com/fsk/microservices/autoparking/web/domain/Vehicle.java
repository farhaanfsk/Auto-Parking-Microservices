package com.fsk.microservices.autoparking.web.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Vehicle {
	private long id;
	private String vehicleNo;
	private boolean isCompanyOwned;
	private VehicleType vehicleType;
	private long employee;

}
