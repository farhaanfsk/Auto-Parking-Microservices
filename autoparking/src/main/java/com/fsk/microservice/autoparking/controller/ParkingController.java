package com.fsk.microservice.autoparking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsk.microservice.autoparking.entity.ParkingResponse;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.service.ParkingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/autoparking")
public class ParkingController {

	private ParkingService parkingService;

	@Autowired
	public ParkingController(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@GetMapping("/slots/{officeId}")
	public List<Slot> listAvailableSlots(@PathVariable int officeId, @RequestParam LocalDateTime startTime,
			LocalDateTime endTime) {
		log.info("id found is : {}", officeId);
		parkingService.checkForValidBookingTime(startTime, endTime);
		return parkingService.getAllAvailableSlots(officeId, startTime, endTime);
	}

	@PostMapping("/book")
	public ParkingResponse book(@RequestBody SlotBooking slot) {
		return parkingService.bookParking(slot);
	}

	@PostMapping("/cancel")
	public ParkingResponse cancel(@RequestBody SlotBooking slot) {
		return parkingService.cancelBooking(slot);
	}

	@GetMapping("/mybookings")
	public List<SlotBooking> listAllBookings(@RequestBody long empId) {
		return parkingService.getAllBookings(empId);
	}

	@GetMapping("/mybookings/status/{bookingId}")
	public SlotBooking getBookingStatus(@RequestBody long bookingId) {
		return parkingService.getBookingStatus(bookingId);
	}
}
