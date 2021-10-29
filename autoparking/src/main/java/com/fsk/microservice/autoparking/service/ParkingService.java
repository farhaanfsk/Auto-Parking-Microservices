package com.fsk.microservice.autoparking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fsk.microservice.autoparking.entity.Office;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.enums.SlotStatus;
import com.fsk.microservice.autoparking.repository.OfficeRepository;
import com.fsk.microservice.autoparking.repository.SlotRepository;

@Service
public class ParkingService {
	private SlotRepository slotRepo;
	private OfficeRepository offices;
	public ParkingService(SlotRepository slotRepo, OfficeRepository offices) {
		this.slotRepo = slotRepo;
		this.offices = offices;
	}

	public List<Slot> getAllAvailableSlots(int officeId) {
		List<Slot> slots = slotRepo.findSlotByOffice_Id(officeId);
		System.out.println(slots);
		return slots.stream().filter(i -> (!i.isReserved()) && i.getStatus().equals(SlotStatus.AVAILABLE))
				.collect(Collectors.toList());
	}
}
