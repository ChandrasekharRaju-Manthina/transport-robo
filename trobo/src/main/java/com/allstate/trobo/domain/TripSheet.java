package com.allstate.trobo.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TripSheet {

	private Integer[] vehicleCapcities;

	private Date date;

	private Long shiftId;

	private boolean isDrop;

	private List<PickupPoint> pickUpPoints;
	
	private List<Vehicle> vehicles;
	
	private Shift shift;
	
	private String dateString;
	
	private Map<Long, Address> distanceMatrix;

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

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Map<Long, Address> getDistanceMatrix() {
		return distanceMatrix;
	}

	public void setDistanceMatrix(Map<Long, Address> distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}

}
