package com.fsk.microservice.autoparking.booking.service;

import com.fsk.microservice.autoparking.booking.entities.SlotBooking;
import com.fsk.microservice.autoparking.booking.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.booking.repository.SlotBookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private static final LocalDateTime startTime = LocalDateTime.now().plusHours(6);
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(12);
    @Mock
    private SlotBookingRepository slotBookingRepo;

    @Mock
    private SlotBooking slotBooking;
    @InjectMocks
    BookingService service;
    
    @Test
    public void testBookParkingWhenSlotIsNotAvailableForGivenTime() {
        when(slotBooking.getStartTime()).thenReturn(startTime);
        when(slotBooking.getEndTime()).thenReturn(endTime);
        when(slotBooking.getSlotId()).thenReturn(1L);
        when(slotBooking.getEmpId()).thenReturn(Long.valueOf(500001));
        when(slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(), startTime, endTime)).thenReturn(List.of(1L));
        when(slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(), startTime, endTime)).thenReturn(new ArrayList<>());

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.bookParking(slotBooking));
        assertEquals("Unable to book slot as it is not Available for the given time slot",thrown.getMessage());

    }

    @Test
    public void testBookParkingWhenWhenEmployeeHasSlotBookedForGivenTime() {
        when(slotBooking.getStartTime()).thenReturn(startTime);
        when(slotBooking.getEndTime()).thenReturn(endTime);
        when(slotBooking.getSlotId()).thenReturn(1L);
        when(slotBooking.getEmpId()).thenReturn(Long.valueOf(500001));
        when(slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(), startTime, endTime)).thenReturn(new ArrayList<>());
        when(slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(), startTime, endTime)).thenReturn(List.of(1L));

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.bookParking(slotBooking));
        assertEquals("Unable to book slot as it is not Available for the given time slot",thrown.getMessage());
    }

    @Test
    public void testBookParkingWhenWhenGivenTimeIsGreaterThan7Days() {
        when(slotBooking.getStartTime()).thenReturn(LocalDateTime.now().plusDays(7).plusHours(1));

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.bookParking(slotBooking));
        assertEquals("You cannot book parking for Date greater than 7 days from the current date",thrown.getMessage());
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

        assertEquals(202, service.bookParking(slotBooking).getStatusCode().value());
        assertEquals("Slot is Booking is successfully Your booking Id : " + slotBooking.getId()
                + " Slot id : " + slotBooking.getSlotId() + " for Start time : " + slotBooking.getStartTime()
                + " and end time : " + slotBooking.getEndTime(), service.bookParking(slotBooking).getBody());
    }

    @Test
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
    }
}
