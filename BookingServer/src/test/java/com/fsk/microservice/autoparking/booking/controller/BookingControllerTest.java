package com.fsk.microservice.autoparking.booking.controller;

import com.fsk.microservice.autoparking.booking.entities.SlotBooking;
import com.fsk.microservice.autoparking.booking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    MockMvc mockmvc;
    @MockBean
    BookingService service;
    @MockBean
    SlotBooking slotBooking;
    private static final LocalDateTime startTime = LocalDateTime.now().plusHours(6);
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(12);
    @Mock
    private SlotBookingRepository slotBookingRepo;

    @Test
    public void testBook() throws Exception {
        /*when(slotBooking.getStartTime()).thenReturn(startTime);
        when(slotBooking.getEndTime()).thenReturn(endTime);
        when(slotBooking.getSlotId()).thenReturn(1L);
        when(slotBooking.getId()).thenReturn(1L);
        when(slotBooking.getEmpId()).thenReturn(Long.valueOf(500001));
        when(slotBookingRepo.findBySlotAndTimeBetween(slotBooking.getSlotId(), startTime, endTime)).thenReturn(new ArrayList<>());
        when(slotBookingRepo.findByEmpIdAndTimeBetween(slotBooking.getEmpId(), startTime, endTime)).thenReturn(new ArrayList<>());
        when(slotBookingRepo.save(slotBooking)).thenReturn(slotBooking);*/
        given(service.bookParking(slotBooking))
                .willReturn(new ResponseEntity<>("Test", HttpStatus.ACCEPTED));
        mockmvc.perform(post("/api/booking/book"))
                .andExpect(status().isAccepted());
    }

}
