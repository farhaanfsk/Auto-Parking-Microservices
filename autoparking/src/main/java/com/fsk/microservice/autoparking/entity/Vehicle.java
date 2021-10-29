package com.fsk.microservice.autoparking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fsk.microservice.autoparking.enums.VehicleType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Vehicle")
@Getter
@Setter
public class Vehicle {
	@Id
	private long id;
	@Column(name = "vehicle_no")
	private String vehicleNo;
	@Column(name = "isCompanyOwned", nullable = false, columnDefinition = "BIT")
	private boolean isCompanyOwned;
	@Enumerated(EnumType.STRING)
	@Column(name = "vehicle_type")
	private VehicleType vehicleType;
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;

}
