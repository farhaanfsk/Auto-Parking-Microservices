package com.fsk.microservice.autoparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
	
	//@Query("SELECT T FROM Slot T WHERE T.office.id = ?1")
	public List<Slot> findSlotByOffice_Id(int officeId);

}
