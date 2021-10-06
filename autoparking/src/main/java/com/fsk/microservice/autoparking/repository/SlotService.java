package com.fsk.microservice.autoparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsk.microservice.autoparking.entity.Slot;

public interface SlotService extends JpaRepository<Slot, Long> {

	public Slot findSlotByEmpId(Long empid);

}
