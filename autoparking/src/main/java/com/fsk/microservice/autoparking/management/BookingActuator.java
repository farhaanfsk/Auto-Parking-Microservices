package com.fsk.microservice.autoparking.management;

import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.repository.SlotBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Endpoint(enableByDefault = true,id = "bookings")
public class BookingActuator {

    @Autowired
    SlotBookingRepository repo;

    @ReadOperation
    public List<SlotBooking> getAllBookings(){
        return repo.findAll();
    }
}
