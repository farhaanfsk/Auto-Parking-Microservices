package com.fsk.microservice.autoparking.booking.service;

import com.fsk.microservice.autoparking.booking.entities.Employee;
import com.fsk.microservice.autoparking.booking.entities.Slot;
import com.fsk.microservice.autoparking.booking.entities.SlotBooking;
import com.fsk.microservice.autoparking.booking.entities.Vehicle;
import com.fsk.microservice.autoparking.booking.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.booking.repository.EmployeeRepository;
import com.fsk.microservice.autoparking.booking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.booking.repository.SlotRepository;
import com.fsk.microservice.autoparking.booking.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingService {
    private final SlotRepository slotRepo;
    private final SlotBookingRepository slotBookingRepo;
    private final VehicleRepository vehicleRepo;
    private final EmployeeRepository empRepo;

    public BookingService(SlotRepository slotRepo, SlotBookingRepository slotBookingRepo, VehicleRepository vehicleRepo,
                          EmployeeRepository empRepo) {
        this.slotRepo = slotRepo;
        this.slotBookingRepo = slotBookingRepo;
        this.vehicleRepo = vehicleRepo;
        this.empRepo = empRepo;
    }

    public ResponseEntity<String> bookParking(SlotBooking slotBooking) {
        if (slotBooking.getStartTime().isAfter(LocalDateTime.now().plusDays(7))) {
            throw new InvalidValueException("You cannot book parking for Date greater than 7 days from the current date");
        }
        List<Long> bookedSlots = slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(),
                slotBooking.getStartTime(), slotBooking.getEndTime());
        List<Long> empBookedSlots = slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(),
                slotBooking.getStartTime(), slotBooking.getEndTime());
        if (bookedSlots.isEmpty() && empBookedSlots.isEmpty()) {
            SlotBooking booking = slotBookingRepo.save(slotBooking);
            return new ResponseEntity<>(
                    "Slot is Booking is successfully Your booking Id : " + booking.getId()
                            + " Slot id : " + booking.getSlotId() + " for Start time : " + booking.getStartTime()
                            + " and end time : " + booking.getEndTime(), HttpStatus.ACCEPTED);
        } else {
            throw new InvalidValueException("Unable to book slot as it is not Available for the given time slot");
        }

    }

    public List<ResponseEntity<String>> bookParkingForContinuousDays(SlotBooking slot, long days) {
        if (days > 7 || slot.getStartTime().isAfter(LocalDateTime.now().plusDays(7))) {
            throw new InvalidValueException("You can only book parking for a max of 7 days");
        }
        List<ResponseEntity<String>> responses = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            SlotBooking newBooking = new SlotBooking(slot);
            newBooking.setStartTime(slot.getStartTime().plusDays(i));
            newBooking.setEndTime(slot.getEndTime().plusDays(i));
            responses.add(bookParking(newBooking));
        }
        return responses;
    }

    public List<ResponseEntity<String>> bookParkingForSpecificDays(List<SlotBooking> slots) {
        List<ResponseEntity<String>> responses = new ArrayList<>();
        for (SlotBooking slotBooking : slots) {
            try {
                checkForValidParkingData(slotBooking);
                checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
                responses.add(bookParking(slotBooking));
            } catch (InvalidValueException e) {
                responses.add(new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST));
            }
        }
        slots.forEach(i -> responses.add(bookParking(i)));
        return responses;
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

    public void checkForValidBookingTime(LocalDateTime startTime, LocalDateTime endTime) {
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        log.info("Difference in hours of start and end time is : {}", hours);
        if (hours > 10 || hours < 4 ){
            throw new InvalidValueException(
                    "The difference in start and end time should be minimum of 4hrs to max of 10 hrs");
        } else if(startTime.isBefore(LocalDateTime.now().plusHours(1).minusMinutes(5))){
            throw new InvalidValueException(
                    "The start and end time provided are invalid, provided time should be at least 1 hr from current time");
        }
    }
}
