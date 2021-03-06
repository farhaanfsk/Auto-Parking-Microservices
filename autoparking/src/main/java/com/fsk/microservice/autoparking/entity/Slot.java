package com.fsk.microservice.autoparking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fsk.microservice.autoparking.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
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
