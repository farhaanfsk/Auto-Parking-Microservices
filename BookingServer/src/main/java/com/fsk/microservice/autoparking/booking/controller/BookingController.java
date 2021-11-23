package com.fsk.microservice.autoparking.booking.controller;

import com.fsk.microservice.autoparking.booking.entities.SlotBooking;
import com.fsk.microservice.autoparking.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autoparking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Operation(summary = "Book a parking slot")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book")
    public ResponseEntity<String> book(@RequestBody SlotBooking slotBooking) {
        bookingService.checkForValidParkingData(slotBooking);
        bookingService.checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
        return bookingService.bookParking(slotBooking);
    }

    @Operation(summary = "Book parking slot for a week from now")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/week")
    public List<ResponseEntity<String>> bookForWeek(@RequestBody SlotBooking slotBooking) {
        bookingService.checkForValidParkingData(slotBooking);
        bookingService.checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
        return bookingService.bookParkingForContinuousDays(slotBooking, 7);
    }

    @Operation(summary = "Book parking slot for continuous no of days")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/multiple/{days}")
    public List<ResponseEntity<String>> bookForNoOfDays(@RequestBody SlotBooking slotBooking, @PathVariable long days) {
        bookingService.checkForValidParkingData(slotBooking);
        bookingService.checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
        return bookingService.bookParkingForContinuousDays(slotBooking, days);
    }

    @Operation(summary = "Book parking slot for specific dats of week")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/multiple")
    public List<ResponseEntity<String>> bookForSpecificDays(@RequestBody List<SlotBooking> slotBooking) {
        return bookingService.bookParkingForSpecificDays(slotBooking);
    }
}
