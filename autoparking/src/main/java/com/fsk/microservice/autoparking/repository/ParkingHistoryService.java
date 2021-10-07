package com.fsk.microservice.autoparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsk.microservice.autoparking.entity.ParkingHistory;

public interface ParkingHistoryService extends JpaRepository<ParkingHistory, Long>{

}
