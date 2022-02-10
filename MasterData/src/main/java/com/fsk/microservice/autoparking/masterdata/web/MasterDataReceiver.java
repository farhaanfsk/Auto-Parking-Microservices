package com.fsk.microservice.autoparking.masterdata.web;

import com.fsk.microservice.autoparking.masterdata.service.MasterDataService;
import com.fsk.microservice.autoparking.masterdata.web.entities.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MasterDataReceiver {

    private final MasterDataService masterDataService;

    @JmsListener(destination = "vehicle.add.queue")
    public void addVehicle(Vehicle vehicle){
        masterDataService.addVehicle(vehicle);
    }
}
