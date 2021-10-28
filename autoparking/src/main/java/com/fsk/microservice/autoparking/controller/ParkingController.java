package com.fsk.microservice.autoparking.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsk.microservice.autoparking.entity.ParkingHistory;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.enums.BookingStatus;
import com.fsk.microservice.autoparking.enums.SlotStatus;
import com.fsk.microservice.autoparking.repository.EmployeeService;
import com.fsk.microservice.autoparking.repository.ParkingHistoryService;
import com.fsk.microservice.autoparking.repository.SlotService;

@RestController
@RequestMapping("/api/autoparking")
public class ParkingController {

	SlotService slots;
	EmployeeService empService;
	ParkingHistoryService parkinghistory;

	@Autowired
	public ParkingController(SlotService slots, EmployeeService empService, ParkingHistoryService history) {
		this.slots = slots;
		this.empService = empService;
		this.parkinghistory = history;
	}

	@GetMapping("/slots")
	public List<Slot> listAvailableSlots(@RequestBody long empid) {
		return new ArrayList<Slot>();
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
