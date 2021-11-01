package com.fsk.microservice.autoparking.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fsk.microservice.autoparking.enums.City;

import lombok.Getter;
import lombok.Setter;

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
