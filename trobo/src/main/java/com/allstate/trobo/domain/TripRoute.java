package com.allstate.trobo.domain;

import java.util.Date;
import java.util.List;

public class TripRoute {

	private String routeId;
	private Date tripDate;
	private String tripType;
	private Long shiftId;
	private Long vehicleId;
	private Long tripDistance;
	private String paid;
	private List<TripRouteEmployee> employees;
	private String vehicleNumber;

	public TripRoute(String routeId, Date tripDate, String tripType,
			long shiftId, long vehicleId, long tripDistance, String paid, String vehicleNumber) {
		this.routeId = routeId;
		this.tripDate = tripDate;
		this.tripType = tripType;
		this.shiftId = shiftId;
		this.vehicleId = vehicleId;
		this.tripDistance = tripDistance;
		this.paid = paid;
		this.vehicleNumber = vehicleNumber;
	}

	public TripRoute() {
		// TODO Auto-generated constructor stub
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getTripDistance() {
		return tripDistance;
	}

	public void setTripDistance(Long tripDistance) {
		this.tripDistance = tripDistance;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public List<TripRouteEmployee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<TripRouteEmployee> employees) {
		this.employees = employees;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

}
