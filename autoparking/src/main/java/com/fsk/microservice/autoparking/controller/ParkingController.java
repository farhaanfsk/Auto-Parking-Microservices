package com.fsk.microservice.autoparking.controller;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/autoparking")
@Tag(name = "Parking", description = "Parking API")
public class ParkingController {

    private ParkingService parkingService;
    private RestTemplate rest;
    @Value("${autoparking.booking.server}")
    private String bookingUri;
    @Value("${autoparking.cancellation.server}")
    private String cancellationUri;

    public ParkingController(ParkingService parkingService, RestTemplate rest) {
        this.parkingService = parkingService;
        this.rest = rest;
    }

    @Operation(summary = "Book a parking slot")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book")
    public ResponseEntity<String> book(@RequestBody SlotBooking slotBooking) {
        ResponseEntity<String> resp = rest.postForEntity
                (bookingUri + "/book", slotBooking, String.class);
        log.info(resp.getBody());
        log.info(resp.getStatusCode() + "");
        return resp;
    }

    @Operation(summary = "Book parking slot for a week from now")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/week")
    public List<ResponseEntity<String>> bookForWeek(@RequestBody SlotBooking slotBooking) {
        return rest.postForEntity(bookingUri + "/book/week", slotBooking, List.class).getBody();
    }

    @Operation(summary = "Book parking slot for continuous no of days")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/multiple/{days}")
    public List<ResponseEntity<String>> bookForNoOfDays(@RequestBody SlotBooking slotBooking, @PathVariable long days) {
        return rest.postForEntity(bookingUri + "/book/multiple/" + days, slotBooking, List.class).getBody();
    }

    @Operation(summary = "Book parking slot for specific dats of week")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/book/multiple")
    public List<ResponseEntity<String>> bookForSpecificDays(@RequestBody List<SlotBooking> slotBooking) {
        return rest.postForEntity(bookingUri + "/book/multiple", slotBooking, List.class).getBody();
    }

    @Operation(summary = "Cancel a booked parking slot")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody SlotBooking slot) {
        return rest.postForEntity(cancellationUri + "/cancel", slot, String.class);
    }

    @Operation(summary = "Cancel multiple booked parking slots")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/cancel/multiple")
    public List<ResponseEntity<String>> cancel(@RequestBody List<SlotBooking> slots) {
        return rest.postForEntity(cancellationUri + "/cancel/multiple", slots, List.class).getBody();
    }

    @GetMapping(value = "/slots/{officeId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            /*,headers = {"Accept=application/xml","Accept=application/json"}*/)
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
    @GetMapping("/{empId}/mybookings")
    public List<SlotBooking> listAllBookings(@PathVariable long empId) {
        return parkingService.getAllEmployeeBookings(empId);
    }

    @Operation(summary = "view a specific booking")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @GetMapping("/{empId}/mybookings/{bookingId}")
    public SlotBooking getBooking(@PathVariable long empId, @RequestBody long bookingId) {
        return parkingService.getBookingDetails(empId, bookingId);
    }
}
