package com.fsk.microservice.autoparking.booking.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fsk.microservice.autoparking.booking.enums.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Setter
@Getter
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String address;
	@Enumerated(EnumType.STRING)
	private City city;
	@JsonManagedReference
	@OneToMany(mappedBy = "office")
	List<Slot> slots;
}
