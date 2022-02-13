package com.fsk.microservice.autoparking.masterdata.service;

import com.fsk.microservice.autoparking.masterdata.domain.Employee;
import com.fsk.microservice.autoparking.masterdata.repositories.EmployeeRepository;
import com.fsk.microservice.autoparking.masterdata.repositories.VehicleRepository;
import com.fsk.microservice.autoparking.masterdata.web.entities.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterDataService {

    private final VehicleRepository vehicleRepository;
    private final EmployeeRepository employeeRepository;

    public void addVehicle(Vehicle vehicle) {
        Employee e = employeeRepository.findById(vehicle.getEmployee()).orElseThrow();
        com.fsk.microservice.autoparking.masterdata.domain.Vehicle v = com.fsk.microservice.autoparking.masterdata.domain.Vehicle.builder()
                .employee(e).id(vehicle.getId()).vehicleNo(vehicle.getVehicleNo()).vehicleType(vehicle.getVehicleType())
                .build();
        log.info(v.toString());
        vehicleRepository.save(v);
    }
}
