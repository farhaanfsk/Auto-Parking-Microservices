package com.fsk.microservices.autoparking.web.services;

import com.fsk.microservices.autoparking.web.domain.Slot;
import com.fsk.microservices.autoparking.web.domain.SlotAvailability;

import java.util.List;

public interface AutoParkingService {
    public List<Slot> getAvailableSlots(SlotAvailability slotAvailability);
}
