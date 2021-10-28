package com.fsk.microservice.autoparking.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fsk.microservice.autoparking.enums.City;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
public class Office {
	@Id
	private int id;
	private String name;
	@Enumerated(EnumType.STRING)
	private City city;
	@OneToMany(mappedBy = "office", cascade = CascadeType.REMOVE)
	List<Slot> slots;
}
