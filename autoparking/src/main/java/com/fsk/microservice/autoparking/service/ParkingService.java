package com.fsk.microservice.autoparking.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fsk.microservice.autoparking.entity.Employee;
import com.fsk.microservice.autoparking.entity.ParkingResponse;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.entity.Vehicle;
import com.fsk.microservice.autoparking.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.repository.EmployeeRepository;
import com.fsk.microservice.autoparking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.repository.SlotRepository;
import com.fsk.microservice.autoparking.repository.VehicleRepository;

@Service
public class ParkingService {
	private SlotRepository slotRepo;
	private SlotBookingRepository slotBookingRepo;
	private VehicleRepository vehicleRepo;
	private EmployeeRepository empRepo;

	public ParkingService(SlotRepository slotRepo, SlotBookingRepository slotBookingRepo, VehicleRepository vehicleRepo,
			EmployeeRepository empRepo) {
		this.slotRepo = slotRepo;
		this.slotBookingRepo = slotBookingRepo;
		this.vehicleRepo = vehicleRepo;
		this.empRepo = empRepo;
	}

	public List<Slot> getAllAvailableSlots(int officeId, LocalDateTime StartTime, LocalDateTime endTIme) {
		List<Slot> slots = Optional.ofNullable(slotRepo.findSlotByOfficeId(officeId))
				.orElseThrow(() -> new InvalidValueException("office id provided is invalid :" + officeId));

		List<Long> bookedSlots = slotBookingRepo
				.findBySlotsBetween(slots.stream().map(Slot::getId).collect(Collectors.toList()), StartTime, endTIme);
		return slots.stream().filter(i -> (!bookedSlots.contains(i.getId()))).collect(Collectors.toList());
	}

	public ParkingResponse bookParking(SlotBooking slotBooking) {
		Optional<Slot> slot = slotRepo.findById(slotBooking.getSlotId());
		Optional<Employee> emp = empRepo.findById(slotBooking.getEmpId());
		Optional<Vehicle> vehicle = vehicleRepo.findById(slotBooking.getVehicleId());
		checkForValidParkingData(slot, emp, vehicle);
		checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());

		List<Long> bookedSlots = slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(),
				slotBooking.getStartTime(), slotBooking.getEndTime());
		List<Long> empBookedSlots = slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(),
				slotBooking.getStartTime(), slotBooking.getEndTime());
		if (bookedSlots.isEmpty() && empBookedSlots.isEmpty()) {
			SlotBooking booking = slotBookingRepo.save(slotBooking);
			return new ParkingResponse(HttpStatus.ACCEPTED.value(),
					"Slot is Booking is successfully Your booking Id Slot id : " + booking.getId());
		} else {
			return new ParkingResponse(HttpStatus.BAD_REQUEST.value(),
					"Unable to book slot as it is not AVAILABlE for the given time slot");
		}

	}

	public ParkingResponse cancelBooking(SlotBooking slotBooking) {
		Optional<SlotBooking> booking = slotBookingRepo.findById(slotBooking.getId());
		if (booking.isPresent() && slotBooking.equals(booking.get())) {
			slotBookingRepo.deleteById(slotBooking.getId());
			return new ParkingResponse(HttpStatus.ACCEPTED.value(),
					"Slot is booking is cancelled for Slot id : " + slotBooking.getSlotId());
		} else {
			throw new InvalidValueException("Booking Data is not matching");
		}

	}

	public SlotBooking getBookingStatus(long bookingId) {
		return slotBookingRepo.findById(bookingId)
				.orElseThrow(() -> new InvalidValueException("Booking id provided is invalid :" + bookingId));
	}

	public boolean checkForValidParkingData(Optional<Slot> slot, Optional<Employee> emp, Optional<Vehicle> vehicle) {
		if (slot.isPresent() && emp.isPresent() && vehicle.isPresent()) {
			return true;
		} else {
			List<Long> ids = new ArrayList<>();
			if (slot.isEmpty()) {
				ids.add(slot.get().getId());
			}
			if (emp.isEmpty()) {
				ids.add(emp.get().getEmpId());
			}
			if (vehicle.isEmpty()) {
				ids.add(vehicle.get().getId());
			}
			throw new InvalidValueException("The following entities ids are invalid :" + ids);
		}
	}

	public List<SlotBooking> getAllBookings(long empId) {
		Optional<Employee> emp = empRepo.findById(empId);
		if (emp.isPresent()) {
			return slotBookingRepo.findByEmpId(empId);
		} else {
			throw new InvalidValueException("Employee id provided is invalid :" + empId);
		}
	}

	public void checkForValidBookingTime(LocalDateTime startTime, LocalDateTime endTime) {
		long hours = ChronoUnit.HOURS.between(startTime, endTime);
		if (hours > 10 || hours < 4) {
			throw new InvalidValueException(
					"The difference in start and end time should be minimum of 4hrs to max of 10 hrs");
		}
	}
}
