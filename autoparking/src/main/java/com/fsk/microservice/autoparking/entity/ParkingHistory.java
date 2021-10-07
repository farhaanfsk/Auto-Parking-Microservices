package com.fsk.microservice.autoparking.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fsk.microservice.autoparking.enums.BookingStatus;

@Entity
@Table(name = "park_history")
public class ParkingHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "empid")
	private long empId;
	@Column(name = "slotid")
	private long slotId;
	private Date date;
	@Column(name = "booking_status")
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	

	public ParkingHistory(long empId, long slotId, Date date, BookingStatus status) {
		super();
		this.empId = empId;
		this.slotId = slotId;
		this.date = date;
		this.status = status;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public long getSlotId() {
		return slotId;
	}

	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
