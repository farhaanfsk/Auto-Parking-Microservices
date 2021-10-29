package com.fsk.microservice.autoparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}