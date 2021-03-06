package com.fsk.microservice.autoparking.booking.repository;

import com.fsk.microservice.autoparking.booking.entities.Vehicle;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Vehicle", description = "Vehicle API")
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
