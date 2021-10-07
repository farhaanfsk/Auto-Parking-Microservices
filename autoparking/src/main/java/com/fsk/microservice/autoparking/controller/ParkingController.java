package com.fsk.microservice.autoparking.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsk.microservice.autoparking.entity.ParkingHistory;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.enums.BookingStatus;
import com.fsk.microservice.autoparking.enums.Membership;
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
		Membership empMembership = empService.findSlotByEmpId(empid).getMembership();
		return slots.findAll().stream().filter(i -> Objects.isNull(i.getEmpId())).filter(i -> {
			if (empMembership.equals(Membership.GOLD)) {
				return true;
			} else if (empMembership.equals(Membership.SILVER)) {
				return !i.getMembership().equals(Membership.GOLD);
			} else if (empMembership.equals(Membership.BRONZE)) {
				return !i.getMembership().equals(Membership.GOLD) && !i.getMembership().equals(Membership.SILVER);
			} else {
				return i.getMembership().equals(Membership.NEW);
			}
		}).collect(Collectors.toList());
	}

	@PostMapping("/book")
	public String book(@RequestBody Slot slot) {
		Optional<Slot> checkSlot = slots.findById(slot.getId());
		Long empid = checkSlot.get().getEmpId();
		ParkingHistory history = new ParkingHistory(slot.getEmpId(), slot.getId(), Date.valueOf(LocalDate.now()),
				BookingStatus.BOOKED);
		parkinghistory.save(history);
		if (checkSlot.isPresent() && !Objects.nonNull(empid)) {
			Slot s = slots.findSlotByEmpId(slot.getEmpId());
			if (Objects.nonNull(s)) {
				cancel(s.getId());
			}
			slots.saveAndFlush(slot);
			return "Slot is Booked Successfully";
		} else {
			return "Slot is not available for Booking or Slot does not Exist";
		}
	}

	@PostMapping("/cancel")
	public String cancel(@RequestBody long slotid) {
		Optional<Slot> checkSlot = slots.findById(slotid);
		ParkingHistory history = new ParkingHistory(checkSlot.get().getEmpId(), checkSlot.get().getId(),
				Date.valueOf(LocalDate.now()), BookingStatus.CANCELED);
		parkinghistory.save(history);
		if (checkSlot.isPresent()) {
			checkSlot.get().setEmpId(null);
			checkSlot.get().setStatus(SlotStatus.AVAILABLE);
			slots.saveAndFlush(checkSlot.get());
			return "Slot Booking is cancled Successfully";
		} else {
			return "Slot was never booked or Slot does not Exist";
		}
	}

}
