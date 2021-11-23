package com.fsk.microservice.autoparking.booking.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Employee")
@Getter
@Setter
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private long empId;
	private String name;
	private String email;
	@OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
	private List<Vehicle> vehicles;

}
