package com.fsk.microservice.autoparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
	
	public List<Slot> findSlotByOfficeId(int idOffice);

}
