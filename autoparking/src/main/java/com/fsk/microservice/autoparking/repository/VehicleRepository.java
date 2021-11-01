package com.fsk.microservice.autoparking.repository;

import com.fsk.microservice.autoparking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
