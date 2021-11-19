package com.fsk.microservice.autoparking.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.Employee;

@Repository
@Tag(name = "Employee", description = "Employee API")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
