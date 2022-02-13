package com.fsk.microservice.autoparking.masterdata.web.entities;

import com.fsk.microservice.autoparking.masterdata.domain.VehicleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Vehicle {
	private long id;
	private String vehicleNo;
	private boolean isCompanyOwned;
	private VehicleType vehicleType;
	private long employee;

}
