package com.fsk.microservice.autoparking.masterdata.web;

import com.fsk.microservice.autoparking.masterdata.service.MasterDataService;
import com.fsk.microservice.autoparking.masterdata.web.entities.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MasterDataReceiver {

    private final MasterDataService masterDataService;

    @JmsListener(destination = "vehicle.add.queue")
    public void addVehicle(Vehicle vehicle){
        log.info(vehicle.toString());
        masterDataService.addVehicle(vehicle);
    }
}
