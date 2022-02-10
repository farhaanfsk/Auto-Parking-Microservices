package com.fsk.microservice.autoparking.masterdata.repositories;

import com.fsk.microservice.autoparking.masterdata.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
