package com.fsk.microservice.autoparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.service.ParkingService;

@RestController
@RequestMapping("/api/autoparking")
public class ParkingController {

	private ParkingService parkingService;

	@Autowired
	public ParkingController(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@GetMapping("/slots/{officeId}")
	public List<Slot> listAvailableSlots(@RequestParam int officeId) {
		return parkingService.getAllAvailableSlots(officeId);
	}

	@PostMapping("/book")
	public String book(@RequestBody Slot slot) {
		return "";
	}

	@PostMapping("/cancel")
	public String cancel(@RequestBody long slotid) {
		return "";
	}

}
