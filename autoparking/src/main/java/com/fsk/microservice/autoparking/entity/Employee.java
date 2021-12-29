package com.fsk.microservice.autoparking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
	@NotEmpty
	private String name;
	@Email
	private String email;
	@OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
	private List<Vehicle> vehicles;

}
