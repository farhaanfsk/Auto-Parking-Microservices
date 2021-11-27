package com.fsk.microservice.autoparking.services;

import com.fsk.microservice.autoparking.entity.Employee;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.repository.EmployeeRepository;
import com.fsk.microservice.autoparking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.repository.SlotRepository;
import com.fsk.microservice.autoparking.service.ParkingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
    private static final int officeId = 3;
    private static final LocalDateTime startTime = LocalDateTime.now().plusHours(6);
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(12);
    @Mock
    private SlotRepository slotRepo;
    @Mock
    private EmployeeRepository empRepo;
    @Mock
    private SlotBookingRepository slotBookingRepo;
    @Mock
    private Slot slot;
    @Mock
    private Employee emp;
    @Mock
    private SlotBooking slotBooking;
    @InjectMocks
    ParkingService service;

    @Test
    public void testGetAllAvailableSlotsWhenAllSlotsBooked() {
        when(slot.getId()).thenReturn(1L);
        when(slotRepo.findSlotByOfficeId(officeId)).thenReturn(List.of(slot));
        when(slotBookingRepo.findBySlotsBetween(List.of(slot.getId()), startTime, endTime))
                .thenReturn(List.of(1L));
        assertNotEquals(List.of(slot), service.getAvailableSlotsOfOfficeInATimeSlot(officeId, startTime, endTime));
    }

    @Test
    public void testGetAllAvailableSlotsWhenSlotsAvailable() {
        when(slot.getId()).thenReturn(1L);
        when(slotRepo.findSlotByOfficeId(officeId)).thenReturn(List.of(slot));
        when(slotBookingRepo.findBySlotsBetween(List.of(slot.getId()), startTime, endTime))
                .thenReturn(new ArrayList<>());
        assertEquals(List.of(slot), service.getAvailableSlotsOfOfficeInATimeSlot(officeId, startTime, endTime));
    }


    @Test
    public void testGetBookingDetailsWhenInvalidIdIsProvided() {
        when(slotBookingRepo.findById(1L)).thenReturn(Optional.empty());
        when(empRepo.findById(1L)).thenReturn(Optional.of(emp));

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.getBookingDetails(1, 1));
        assertEquals("Booking id provided is invalid :" + 1, thrown.getMessage());
    }

    @Test
    public void testGetBookingDetailsWhenInvalidEmpIdIsProvided() {
        when(empRepo.findById(1L)).thenReturn(Optional.empty());
        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.getBookingDetails(1, 1));
        assertEquals("Emp id provided is invalid :" + 1, thrown.getMessage());
    }

    @Test
    public void testGetBookingDetails() {
        when(slotBookingRepo.findById(1L)).thenReturn(Optional.of(slotBooking));
        when(empRepo.findById(1L)).thenReturn(Optional.of(emp));
        assertEquals(slotBooking, service.getBookingDetails(1, 1));
    }

    @Test
    public void testGetAllBookings() {
        when(slotBookingRepo.findByEmpId(500001)).thenReturn(List.of(slotBooking));
        when(empRepo.findById(500001L)).thenReturn(Optional.of(emp));
        assertEquals(List.of(slotBooking), service.getAllEmployeeBookings(500001));
    }

}
