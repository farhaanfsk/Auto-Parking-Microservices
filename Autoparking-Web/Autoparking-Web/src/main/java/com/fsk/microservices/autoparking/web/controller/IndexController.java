package com.fsk.microservices.autoparking.web.controller;

import com.fsk.microservices.autoparking.web.domain.Slot;
import com.fsk.microservices.autoparking.web.domain.SlotAvailability;
import com.fsk.microservices.autoparking.web.services.AutoParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final AutoParkingService autoParkingService;

    @RequestMapping
    public String home(Model model) {
        model.addAttribute("SlotAvailability", new SlotAvailability());
        return "index";
    }

    @PostMapping("/slots")
    @PreAuthorize("hasRole('ADMIN') ")
    public String getSlots(@ModelAttribute("slot") SlotAvailability slot, Model model) {
        log.info(slot.toString());
        List<Slot> slots = autoParkingService.getAvailableSlots(slot);
        log.info(slots.toString());
        model.addAttribute("slots",slots);
        return "availableSlots";
    }
}
