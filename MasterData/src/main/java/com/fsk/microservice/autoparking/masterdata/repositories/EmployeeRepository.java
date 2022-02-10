package com.fsk.microservice.autoparking.masterdata.repositories;


import com.fsk.microservice.autoparking.masterdata.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
