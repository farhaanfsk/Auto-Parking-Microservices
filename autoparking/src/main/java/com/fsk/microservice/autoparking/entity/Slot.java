package com.fsk.microservice.autoparking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fsk.microservice.autoparking.enums.SlotStatus;
import com.fsk.microservice.autoparking.enums.VehicleType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Enumerated(EnumType.STRING)
	private SlotStatus status;
	@Column(name = "is_reserved", columnDefinition="BIT")
	private boolean isReserved;
	@Column(name = "vehicle_type")
	@Enumerated(EnumType.STRING)
	private VehicleType slotType;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "office_id")
	private Office office;
	

}
