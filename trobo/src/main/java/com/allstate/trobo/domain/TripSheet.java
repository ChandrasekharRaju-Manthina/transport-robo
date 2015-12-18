package com.allstate.trobo.domain;

import java.util.Date;
import java.util.List;

public class TripSheet {

	private Integer[] vehicleCapcities;

	private Date date;

	private Long shiftId;

	private boolean isDrop;

	private List<PickupPoint> pickUpPoints;

	public Integer[] getVehicleCapcities() {
		return vehicleCapcities;
	}

	public void setVehicleCapcities(Integer[] vehicleCapcities) {
		this.vehicleCapcities = vehicleCapcities;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public boolean isDrop() {
		return isDrop;
	}

	public void setDrop(boolean isDrop) {
		this.isDrop = isDrop;
	}

	public List<PickupPoint> getPickUpPoints() {
		return pickUpPoints;
	}

	public void setPickUpPoints(List<PickupPoint> pickUpPoints) {
		this.pickUpPoints = pickUpPoints;
	}

}
