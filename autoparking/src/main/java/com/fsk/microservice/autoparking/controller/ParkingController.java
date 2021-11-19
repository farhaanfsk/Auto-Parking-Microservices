package com.fsk.microservice.autoparking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsk.microservice.autoparking.entity.ParkingResponse;
import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.service.ParkingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/autoparking")
@Tag(name = "Parking", description = "Parking API")
public class ParkingController {

    private ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/slots/{officeId}")
    @Operation(summary = "Get all available slots of an office")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public List<Slot> listAvailableSlots(@PathVariable int officeId,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                 Optional<LocalDateTime> startTime,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                 Optional<LocalDateTime> endTime) {
        log.info("startTime found is : {}", startTime);
        log.info("endTime found is : {}", endTime);
        LocalDateTime st = startTime.orElseGet(() -> LocalDateTime.now().plusHours(1));
        LocalDateTime et = startTime.orElseGet(() -> LocalDateTime.now().plusHours(9));
        parkingService.checkForValidBookingTime(st, et);
        return parkingService.getAvailableSlotsOfOfficeInATimeSlot(officeId, st, et);
    }

    @Operation(summary = "Book a parking slot")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book")
    public ParkingResponse book(@RequestBody SlotBooking slotBooking) {
        parkingService.checkForValidParkingData(slotBooking);
        parkingService.checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
        return parkingService.bookParking(slotBooking);
    }

    @Operation(summary = "Book parking slot for a week from now")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/week")
    public List<ParkingResponse> bookForWeek(@RequestBody SlotBooking slotBooking) {
        parkingService.checkForValidParkingData(slotBooking);
        parkingService.checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
        return parkingService.bookParkingForContinuousDays(slotBooking, 7);
    }

    @Operation(summary = "Book parking slot for continuous no of days")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/multiple/{days}")
    public List<ParkingResponse> bookForNoOfDays(@RequestBody SlotBooking slotBooking, @PathVariable long days) {
        parkingService.checkForValidParkingData(slotBooking);
        parkingService.checkForValidBookingTime(slotBooking.getStartTime(), slotBooking.getEndTime());
        return parkingService.bookParkingForContinuousDays(slotBooking, days);
    }
    @Operation(summary = "Book parking slot for specific dats of week")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/multiple")
    public List<ParkingResponse> bookForSpecificDays(@RequestBody List<SlotBooking> slotBooking) {
        return parkingService.bookParkingForSpecificDays(slotBooking);
    }

    @Operation(summary = "Cancel a booked parking slot")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/cancel")
    public ParkingResponse cancel(@RequestBody SlotBooking slot) {
        return parkingService.cancelBooking(slot);
    }

    @Operation(summary = "Cancel multiple booked parking slots")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/cancel/multiple")
    public List<ParkingResponse> cancel(@RequestBody List<SlotBooking> slots) {
        return parkingService.cancelMultipleBookings(slots);
    }

    @Operation(summary = "view current employee bookings")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @GetMapping("/mybookings")
    public List<SlotBooking> listAllBookings(@RequestBody long empId) {
        return parkingService.getAllBookings(empId);
    }

    @Operation(summary = "view a specific booking")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @GetMapping("/mybookings/{bookingId}")
    public SlotBooking getBooking(@PathVariable long bookingId) {
        return parkingService.getBookingDetails(bookingId);
    }
}
