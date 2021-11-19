package com.fsk.microservice.autoparking.repository;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.Slot;

@Repository
@Tag(name = "Slot", description = "Slot API")
public interface SlotRepository extends JpaRepository<Slot, Long> {
	
	public List<Slot> findSlotByOfficeId(int idOffice);

}
