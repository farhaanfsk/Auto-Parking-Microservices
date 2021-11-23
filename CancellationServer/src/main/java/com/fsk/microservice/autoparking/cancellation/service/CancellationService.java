package com.fsk.microservice.autoparking.cancellation.service;

import com.fsk.microservice.autoparking.cancellation.entity.SlotBooking;
import com.fsk.microservice.autoparking.cancellation.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.cancellation.repository.SlotBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CancellationService {
    @Autowired
    SlotBookingRepository slotBookingRepo;

    public ResponseEntity<String> cancelBooking(SlotBooking slotBooking) {
        Optional<SlotBooking> booking = slotBookingRepo.findById(slotBooking.getId());
        if (booking.isPresent() && slotBooking.equals(booking.get())) {
            slotBookingRepo.deleteById(slotBooking.getId());
            return new ResponseEntity<>(
                    "Slot is booking is cancelled for Slot id : " + slotBooking.getSlotId(), HttpStatus.ACCEPTED);
        } else {
            throw new InvalidValueException("Booking data is invalid");
        }
    }

    public List<ResponseEntity<String>> cancelMultipleBookings(List<SlotBooking> slotBookings) {
        List<ResponseEntity<String>> responses = new ArrayList<>();
        slotBookings.forEach(i -> responses.add(cancelBooking(i)));
        return responses;
    }
}
