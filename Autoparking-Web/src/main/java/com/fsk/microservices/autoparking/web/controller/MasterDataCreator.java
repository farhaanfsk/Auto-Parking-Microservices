package com.fsk.microservices.autoparking.web.controller;

import com.fsk.microservices.autoparking.web.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/masterdata")
@RequiredArgsConstructor
@Slf4j
public class MasterDataCreator {

    private final JmsTemplate jmsTemplate;

    @PostMapping("/vehicle")
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle){
        log.info(vehicle.toString());
        jmsTemplate.convertAndSend("vehicle.add.queue",vehicle);
        return ResponseEntity.accepted().build();
    }
}
