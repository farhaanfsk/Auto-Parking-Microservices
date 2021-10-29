package com.fsk.microservice.autoparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsk.microservice.autoparking.entity.SlotBooking;

public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {

}
