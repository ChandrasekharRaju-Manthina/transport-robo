package com.allstate.trobo.domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Vehicle {

	private Long id;

	@NotNull
	@Size(min = 5, max = 10)
	private String vehicleNumber;

	@NotNull
	@Digits(integer = 2, fraction = 0)
	@Min(4)
	private Integer seats;

	@NotNull
	private String trackingDeviceLink;

	@NotNull
	private Integer driverId;
	
	private String driverName;

	private String priceUnit;

	public Vehicle() {

	}

	public Vehicle(Long id, String vehicleNumber, Integer seats,
			String trackingDeviceLink, Integer driverId, String driverName) {
		this.id = id;
		this.vehicleNumber = vehicleNumber;
		this.seats = seats;
		this.trackingDeviceLink = trackingDeviceLink;
		this.driverId = driverId;
		this.driverName = driverName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public String getTrackingDeviceLink() {
		return trackingDeviceLink;
	}

	public void setTrackingDeviceLink(String trackingDeviceLink) {
		this.trackingDeviceLink = trackingDeviceLink;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

}
