package com.fsk.microservice.autoparking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.SlotBooking;

@Repository
public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {

	public List<SlotBooking> findByEmpId(long empId);

	@Query("SELECT T.slotId FROM SlotBooking T WHERE T.empId = ?1 AND T.startTime BETWEEN ?2 AND ?3 AND T.endTime BETWEEN ?2 AND ?3")
	public List<Long> findByEmpIdAndTimeBetween(long empId, LocalDateTime startTime, LocalDateTime endTime);

	@Query("SELECT T.slotId FROM SlotBooking T WHERE T.slotId = ?1 AND T.startTime BETWEEN ?2 AND ?3 AND T.endTime BETWEEN ?2 AND ?3")
	public List<Long> findBySlotAndTimeBetween(long slotId, LocalDateTime startTime, LocalDateTime endTime);

	@Query("SELECT T.slotId FROM SlotBooking T WHERE T.slotId in ?1 AND T.startTime BETWEEN ?2 AND ?3 AND T.endTime BETWEEN ?2 AND ?3")
	public List<Long> findBySlotsBetween(List<Long> slotId, LocalDateTime startTime, LocalDateTime endTime);
}
