package com.fsk.microservice.autoparking.booking.repository;


import com.fsk.microservice.autoparking.booking.entities.Slot;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "Slot", description = "Slot API")
public interface SlotRepository extends JpaRepository<Slot, Long> {
	
	public List<Slot> findSlotByOfficeId(int idOffice);

}
