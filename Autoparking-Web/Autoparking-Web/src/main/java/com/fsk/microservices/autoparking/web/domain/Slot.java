package com.fsk.microservices.autoparking.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
	private long id;
	private boolean isReserved;
	private VehicleType slotType;
	private City office;
	

}
