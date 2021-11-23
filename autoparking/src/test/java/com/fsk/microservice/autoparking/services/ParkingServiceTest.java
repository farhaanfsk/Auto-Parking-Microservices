package com.fsk.microservice.autoparking.services;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.exceptions.InvalidValueException;
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
    private SlotBookingRepository slotBookingRepo;
    @Mock
    private Slot slot;
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

    /*@Test
    public void testBookParkingWhenSlotIsNotAvailableForGivenTime() {
        when(slotBooking.getStartTime()).thenReturn(startTime);
        when(slotBooking.getEndTime()).thenReturn(endTime);
        when(slotBooking.getSlotId()).thenReturn(1L);
        when(slotBooking.getEmpId()).thenReturn(Long.valueOf(500001));
        when(slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(), startTime, endTime)).thenReturn(List.of(1L));
        when(slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(), startTime, endTime)).thenReturn(new ArrayList<>());

        assertEquals(400, service.bookParking(slotBooking).getStatusCode());
        assertEquals("Unable to book slot as it is not Available for the given time slot", service.bookParking(slotBooking).getMessage());

    }

    @Test
    public void testBookParkingWhenWhenEmployeeHasSlotBookedForGivenTime() {
        when(slotBooking.getStartTime()).thenReturn(startTime);
        when(slotBooking.getEndTime()).thenReturn(endTime);
        when(slotBooking.getSlotId()).thenReturn(1L);
        when(slotBooking.getEmpId()).thenReturn(Long.valueOf(500001));
        when(slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(), startTime, endTime)).thenReturn(new ArrayList<>());
        when(slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(), startTime, endTime)).thenReturn(List.of(1L));

        assertEquals(400, service.bookParking(slotBooking).getStatusCode());
        assertEquals("Unable to book slot as it is not Available for the given time slot", service.bookParking(slotBooking).getMessage());
    }

    @Test
    public void testBookParkingWhenWhenGivenTimeIsGreaterThan7Days() {
        when(slotBooking.getStartTime()).thenReturn(LocalDateTime.now().plusDays(7).plusHours(1));

        assertEquals(400, service.bookParking(slotBooking).getStatusCode());
        assertEquals("You cannot book parking for Date greater than 7 days from the current date", service.bookParking(slotBooking).getMessage());
    }

    @Test
    public void testSuccessfulBookParking() {
        when(slotBooking.getStartTime()).thenReturn(startTime);
        when(slotBooking.getEndTime()).thenReturn(endTime);
        when(slotBooking.getSlotId()).thenReturn(1L);
        when(slotBooking.getId()).thenReturn(1L);
        when(slotBooking.getEmpId()).thenReturn(Long.valueOf(500001));
        when(slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(), startTime, endTime)).thenReturn(new ArrayList<>());
        when(slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(), startTime, endTime)).thenReturn(new ArrayList<>());
        when(slotBookingRepo.save(slotBooking)).thenReturn(slotBooking);

        assertEquals(202, service.bookParking(slotBooking).getStatusCode());
        assertEquals("Slot is Booking is successfully Your booking Id : " + slotBooking.getId()
                + "Slot id : " + slotBooking.getSlotId() + " for Start time : " + slotBooking.getStartTime()
                + "and end time : " + slotBooking.getEndTime(), service.bookParking(slotBooking).getMessage());
    }

    @Test
    public void testCancelBooking() {
        when(slotBookingRepo.findById(slot.getId())).thenReturn(Optional.of(slotBooking));
        when(slotBooking.getSlotId()).thenReturn(1L);

        assertEquals(202, service.cancelBooking(slotBooking).getStatusCode());
        assertEquals("Slot is booking is cancelled for Slot id : " + slotBooking.getSlotId(), service.cancelBooking(slotBooking).getMessage());

        verify(slotBookingRepo,times(2)).deleteById(any());
    }

    @Test
    public void testCancelBookingWithInvalidBookingData() {
        when(slotBookingRepo.findById(slot.getId())).thenReturn(Optional.empty());

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.cancelBooking(slotBooking));
        assertEquals("Booking data is invalid", thrown.getMessage());
    }
*/
    @Test
    public void testGetBookingDetailsWhenInvalidIdIsProvided() {
        when(slotBookingRepo.findById(1L)).thenReturn(Optional.empty());

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.getBookingDetails(1));
        assertEquals("Booking id provided is invalid :" + 1, thrown.getMessage());
    }

    @Test
    public void testGetBookingDetails() {
        when(slotBookingRepo.findById(1L)).thenReturn(Optional.of(slotBooking));

        assertEquals(slotBooking, service.getBookingDetails(1));
    }

    @Test
    public void testGetAllBookings() {
        when(slotBookingRepo.findByEmpId(500001)).thenReturn(List.of(slotBooking));

        assertEquals(List.of(slotBooking), service.getAllBookings(500001));
    }

    /*@Test
    public void testCheckForInValidBookingTime() {
        assertThrows(InvalidValueException.class, () -> service.checkForValidBookingTime(startTime, endTime.plusHours(5)));
    }

    @Test
    public void testBookParkingForContinuousDaysWhenDateIsAfter7Days() {
        when(slotBooking.getStartTime()).thenReturn(startTime.plusDays(7));
        assertThrows(InvalidValueException.class, () -> service.bookParkingForContinuousDays(slotBooking,7));
    }

    @Test
    public void testBookParkingForContinuousDaysWhenNoOfDaysIsGreaterThan7Days() {
        assertThrows(InvalidValueException.class, () -> service.bookParkingForContinuousDays(slotBooking,8));
    }*/
}
