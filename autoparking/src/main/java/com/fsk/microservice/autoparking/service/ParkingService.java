package com.fsk.microservice.autoparking.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        if (slotBooking.getStartTime().isAfter(LocalDateTime.now().plusDays(7))) {
            return new ParkingResponse(HttpStatus.BAD_REQUEST.value(),
                    "You cannot book parking for Date greater than 7 days from the current date");
        }
        List<Long> bookedSlots = slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(),
                slotBooking.getStartTime(), slotBooking.getEndTime());
        List<Long> empBookedSlots = slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(),
                slotBooking.getStartTime(), slotBooking.getEndTime());
        if (bookedSlots.isEmpty() && empBookedSlots.isEmpty()) {
            SlotBooking booking = slotBookingRepo.save(slotBooking);
            return new ParkingResponse(HttpStatus.ACCEPTED.value(),
                    "Slot is Booking is successfully Your booking Id : " + booking.getId()
                            + "Slot id : " + booking.getSlotId() + " for Start time : " + booking.getStartTime()
                            + "and end time : " + booking.getEndTime());
        } else {
            return new ParkingResponse(HttpStatus.BAD_REQUEST.value(),
                    "Unable to book slot as it is not Available for the given time slot");
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

    public void checkForValidParkingData(SlotBooking slotBooking) {
        Optional<Slot> slot = slotRepo.findById(slotBooking.getSlotId());
        Optional<Employee> emp = empRepo.findById(slotBooking.getEmpId());
        Optional<Vehicle> vehicle = vehicleRepo.findById(slotBooking.getVehicleId());
        if (!(slot.isPresent() && emp.isPresent() && vehicle.isPresent())) {
            String msg = "";
            msg = slot.isEmpty() ? msg + ("Slot") : msg;
            msg = emp.isEmpty() ? msg + ("Employee") : msg;
            msg = vehicle.isEmpty() ? msg + ("vehicle") : msg;
            throw new InvalidValueException("The following entities are invalid :" + msg);
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
        log.info("Difference in hours of start and end time is : {}", hours);
        if (hours > 10 || hours < 4 || startTime.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new InvalidValueException(
                    "The difference in start and end time should be minimum of 4hrs to max of 10 hrs");
        }
    }

    public List<ParkingResponse> bookParkingForContinuousDays(SlotBooking slot, long days) {
        if (days > 7 || slot.getStartTime().isAfter(LocalDateTime.now().plusDays(7))) {
            throw new InvalidValueException("You can only book parking for a max of 7 days");
        }
        List<ParkingResponse> responses = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            SlotBooking newBooking = new SlotBooking(slot);
            newBooking.setStartTime(slot.getStartTime().plusDays(i));
            newBooking.setEndTime(slot.getEndTime().plusDays(i));
            responses.add(bookParking(newBooking));
        }
        return responses;
    }
}
