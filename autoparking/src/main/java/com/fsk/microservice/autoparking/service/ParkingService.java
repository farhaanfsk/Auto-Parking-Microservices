package com.fsk.microservice.autoparking.service;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.repository.SlotRepository;
import com.fsk.microservice.autoparking.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParkingService {
    private final SlotRepository slotRepo;
    private final SlotBookingRepository slotBookingRepo;
    private final VehicleRepository vehicleRepo;

    public ParkingService(SlotRepository slotRepo, SlotBookingRepository slotBookingRepo, VehicleRepository vehicleRepo) {
        this.slotRepo = slotRepo;
        this.slotBookingRepo = slotBookingRepo;
        this.vehicleRepo = vehicleRepo;
    }

    public List<Slot> getAvailableSlotsOfOfficeInATimeSlot(int officeId, LocalDateTime StartTime, LocalDateTime endTIme) {
        List<Slot> slots = Optional.ofNullable(slotRepo.findSlotByOfficeId(officeId))
                .orElseThrow(() -> new InvalidValueException("office id provided is invalid :" + officeId));

        List<Long> bookedSlots = slotBookingRepo
                .findBySlotsBetween(slots.stream().map(Slot::getId).collect(Collectors.toList()), StartTime, endTIme);
        return slots.stream().filter(i -> (!bookedSlots.contains(i.getId()))).collect(Collectors.toList());
    }

    public SlotBooking getBookingDetails(long bookingId) {
        return slotBookingRepo.findById(bookingId)
                .orElseThrow(() -> new InvalidValueException("Booking id provided is invalid :" + bookingId));
    }



    public List<SlotBooking> getAllBookings(long empId) {
        return slotBookingRepo.findByEmpId(empId);
    }

}
