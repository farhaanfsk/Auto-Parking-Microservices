package com.fsk.microservices.autoparking.web.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotAvailability {
    private String startTime;
    private String endTime;
    private String office;
}
