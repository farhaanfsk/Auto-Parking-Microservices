package com.fsk.microservices.autoparking.web.services;

import com.fsk.microservices.autoparking.web.domain.Slot;
import com.fsk.microservices.autoparking.web.domain.SlotAvailability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoParkingServiceImpl implements AutoParkingService {

    private final RestTemplate restTemplate;

    @Override
    public List<Slot> getAvailableSlots(SlotAvailability slotAvailability) {
        DateTimeFormatter uiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HttpHeaders headers = new HttpHeaders();
        return restTemplate.
                getForObject("http://localhost:8081/api/autoparking/slots/{officeId}?startTime={startTime}&endTime={startTime}"
                        , List.class, slotAvailability.getOffice(),
                        LocalDateTime.parse(slotAvailability.getStartTime(), uiFormat).format(dateTimeFormat)
                        , LocalDateTime.parse(slotAvailability.getEndTime(), uiFormat).format(dateTimeFormat));
    }
}
