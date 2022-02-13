package com.fsk.microservices.autoparking.web.domain;

import lombok.Getter;

@Getter
public enum City {
    HYDERABAD(1), PUNE(2), BANGALORE(3), CHENNAI(4);

    City(int i) {
    }
    int i;

}
