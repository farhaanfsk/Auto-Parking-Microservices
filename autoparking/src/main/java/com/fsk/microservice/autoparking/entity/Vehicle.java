package com.fsk.microservice.autoparking.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fsk.microservice.autoparking.enums.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Vehicle")
@Getter
@Setter
@AllArgsConstructor
public class Vehicle {
	private long id;
	private String vehicleNo;
	private boolean isCompanyOwned;
	private long empId;
	@Enumerated(EnumType.STRING)
	private VehicleType vehicleType;
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;

}
