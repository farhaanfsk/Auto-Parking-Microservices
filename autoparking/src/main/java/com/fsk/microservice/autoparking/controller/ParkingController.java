package com.fsk.microservice.autoparking.controller;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        return parkingService.getAvailableSlotsOfOfficeInATimeSlot(officeId, st, et);
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
