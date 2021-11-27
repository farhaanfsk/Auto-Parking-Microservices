package com.fsk.microservice.autoparking.repository;

import com.fsk.microservice.autoparking.entity.Employee;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Employee", description = "Employee API")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
