package com.fsk.microservices.autoparking.web.controller;

import com.fsk.microservices.autoparking.web.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MasterDataCreator {

    private final JmsTemplate jmsTemplate;

    @PostMapping
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle){
        jmsTemplate.convertAndSend(vehicle);
        return ResponseEntity.noContent().build();
    }
}
