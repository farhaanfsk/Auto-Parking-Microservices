package com.fsk.microservice.autoparking.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fsk.microservice.autoparking.enums.SlotStatus;
import com.fsk.microservice.autoparking.enums.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private SlotStatus status;
	private boolean isReserved;
	@Enumerated(EnumType.STRING)
	private VehicleType slotType;
	@ManyToOne
	@JoinColumn
	private Office office;
	

}
