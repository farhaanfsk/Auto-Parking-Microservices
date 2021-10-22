package com.fsk.microservice.autoparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fsk.microservice.autoparking.entity.Employee;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.enums.Membership;

public interface EmployeeService extends JpaRepository<Employee, Long> {

	@Query("SELECT T.membership FROM Employee T WHERE T.empId = ?1")
	public Membership findMembershipByEmpId(Long empid);

	public Slot findSlotByEmpId(long empid);
}
