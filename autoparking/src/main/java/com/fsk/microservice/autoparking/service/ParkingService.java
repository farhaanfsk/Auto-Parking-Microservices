package com.fsk.microservice.autoparking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fsk.microservice.autoparking.entity.Employee;
import com.fsk.microservice.autoparking.entity.Vehicle;
import com.fsk.microservice.autoparking.exceptions.InvalidValueException;
import com.fsk.microservice.autoparking.repository.EmployeeRepository;
import com.fsk.microservice.autoparking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import com.fsk.microservice.autoparking.entity.Slot;
import com.fsk.microservice.autoparking.entity.SlotBooking;
import com.fsk.microservice.autoparking.enums.SlotStatus;
import com.fsk.microservice.autoparking.repository.SlotBookingRepository;
import com.fsk.microservice.autoparking.repository.SlotRepository;

@Service
public class ParkingService {
    private SlotRepository slotRepo;
    private SlotBookingRepository slotBookingRepo;
    private VehicleRepository vehicleRepo;
    private EmployeeRepository empRepo;

    public ParkingService(SlotRepository slotRepo, SlotBookingRepository slotBookingRepo, VehicleRepository vehicleRepo, EmployeeRepository empRepo) {
        this.slotRepo = slotRepo;
        this.slotBookingRepo = slotBookingRepo;
        this.vehicleRepo = vehicleRepo;
        this.empRepo = empRepo;
    }

    public List<Slot> getAllAvailableSlots(int officeId) {
        List<Slot> slots = slotRepo.findSlotByOfficeId(officeId);
        return slots.stream().filter(i -> (!i.isReserved()) && i.getStatus().equals(SlotStatus.AVAILABLE))
                .collect(Collectors.toList());
    }

    public String bookParking(SlotBooking slotBooking) {
        Optional<Slot> slot = slotRepo.findById(slotBooking.getSlotId());
        Optional<Employee> emp = empRepo.findById(slotBooking.getEmpId());
        Optional<Vehicle> vehicle = vehicleRepo.findById(slotBooking.getVehicleId());
        if (slot.isPresent() && emp.isPresent() && vehicle.isPresent()) {
            Slot s = slot.get();
            if (s.getStatus().equals(SlotStatus.BOOKED)) {
                return "Unable to book slot as it is not AVAILABlE";
            } else {
                s.setStatus(SlotStatus.BOOKED);
                slotRepo.save(s);
                SlotBooking booking = slotBookingRepo.save(slotBooking);
                return "slot booked successfully";
            }
        } else {
            List<Object> objs = new ArrayList<>();
            if (slot.isEmpty()) {
                objs.add(slot);
            }
            if (emp.isEmpty()) {
                objs.add(emp);
            }
            if (vehicle.isEmpty()) {
                objs.add(vehicle);
            }
            return "The following entities are not found :" + objs;
        }

    }
}
