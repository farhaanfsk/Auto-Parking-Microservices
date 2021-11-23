package com.fsk.microservice.autoparking.cancellation.controller;

import com.fsk.microservice.autoparking.cancellation.entity.SlotBooking;
import com.fsk.microservice.autoparking.cancellation.service.CancellationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CancellationController {
    @Autowired
    CancellationService service;

    @Operation(summary = "Cancel a booked parking slot")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody SlotBooking slot) {
        return service.cancelBooking(slot);
    }

    @Operation(summary = "Cancel multiple booked parking slots")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping("/cancel/multiple")
    public List<ResponseEntity<String>> cancel(@RequestBody List<SlotBooking> slots) {
        return service.cancelMultipleBookings(slots);
    }
}
