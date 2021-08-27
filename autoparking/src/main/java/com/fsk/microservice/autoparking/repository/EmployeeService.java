package com.fsk.microservice.autoparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsk.microservice.autoparking.entity.Employee;

public interface EmployeeService extends JpaRepository<Employee, Long> {

}
