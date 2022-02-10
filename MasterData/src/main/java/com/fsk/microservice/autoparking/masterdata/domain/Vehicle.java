package com.fsk.microservice.autoparking.masterdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Vehicle")
@Getter
@Setter
@Builder
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
