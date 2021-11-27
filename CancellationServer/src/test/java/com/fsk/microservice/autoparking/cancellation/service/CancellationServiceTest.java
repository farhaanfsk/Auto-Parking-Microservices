package com.fsk.microservice.autoparking.cancellation.service;

import com.fsk.microservice.autoparking.cancellation.entity.Slot;
import com.fsk.microservice.autoparking.cancellation.entity.SlotBooking;
import com.fsk.microservice.autoparking.cancellation.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.cancellation.repository.SlotBookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CancellationServiceTest {

    @Mock
    private SlotBookingRepository slotBookingRepo;
    @Mock
    private Slot slot;
    @Mock
    private SlotBooking slotBooking;
    @InjectMocks
    CancellationService service;

    @Test
    public void testCancelBooking() {
        when(slotBookingRepo.findById(slot.getId())).thenReturn(Optional.of(slotBooking));
        when(slotBooking.getSlotId()).thenReturn(1L);

        assertEquals(202, service.cancelBooking(slotBooking).getStatusCode().value());
        assertEquals("Slot is booking is cancelled for Slot id : " + slotBooking.getSlotId(), service.cancelBooking(slotBooking).getBody());

        verify(slotBookingRepo,times(2)).deleteById(any());
    }

    @Test
    public void testCancelBookingWithInvalidBookingData() {
        when(slotBookingRepo.findById(slot.getId())).thenReturn(Optional.empty());

        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> service.cancelBooking(slotBooking));
        assertEquals("Booking data is invalid", thrown.getMessage());
    }
}
