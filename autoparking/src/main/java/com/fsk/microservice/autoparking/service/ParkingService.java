package com.fsk.microservice.autoparking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.enums.SlotStatus;
import com.fsk.microservice.autoparking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.repository.SlotRepository;

@Service
public class ParkingService {
	private SlotRepository slotRepo;
	private SlotBookingRepository slotBookingRepo;

	public ParkingService(SlotRepository slotRepo, SlotBookingRepository slotBookingRepo) {
		this.slotRepo = slotRepo;
		this.slotBookingRepo = slotBookingRepo;
	}

	public List<Slot> getAllAvailableSlots(int officeId) {
		List<Slot> slots = slotRepo.findSlotByOfficeId(officeId);
		return slots.stream().filter(i -> (!i.isReserved()) && i.getStatus().equals(SlotStatus.AVAILABLE))
				.collect(Collectors.toList());
	}

	public SlotBooking bookParking(SlotBooking slotBooking) {
		return slotBookingRepo.save(slotBooking);
	}
}
