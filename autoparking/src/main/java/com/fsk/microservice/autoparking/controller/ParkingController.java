package com.fsk.microservice.autoparking.controller;

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

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.enums.Status;
import com.fsk.microservice.autoparking.repository.SlotService;

@RestController
@RequestMapping("/api/autoparking")
public class ParkingController {

	@Autowired
	SlotService slots;

	@GetMapping("/slots")
	public List<Slot> listSlots() {
		return slots.findAll().stream().filter(i -> Objects.isNull(i.getEmpId())).collect(Collectors.toList());
	}

	@PostMapping("/book")
	public String book(@RequestBody Slot slot) {
		Optional<Slot> checkSlot = slots.findById(slot.getId());
		Long empid = checkSlot.get().getEmpId();
		if (checkSlot.isPresent() && !Objects.nonNull(empid)) {
			Slot s = slots.findSlotByEmpId(slot.getEmpId());
			if (Objects.isNull(s)) {
				slots.saveAndFlush(slot);
			} else {
				cancel(s);
				slots.saveAndFlush(slot);
			}
			return "Slot is Booked Successfully";
		} else {
			return "Slot is not available for Booking or Slot does not Exist";
		}
	}

	@PostMapping("/cancel")
	public String cancel(@RequestBody Slot slot) {
		Optional<Slot> checkSlot = slots.findById(slot.getId());
		if (checkSlot.isPresent()) {
			checkSlot.get().setEmpId(null);
			checkSlot.get().setStatus(Status.AVAILABLE);
			slots.saveAndFlush(checkSlot.get());
			return "Slot Booking is cancled Successfully";
		} else {
			return "Slot was never booked or Slot does not Exist";
		}
	}

}
