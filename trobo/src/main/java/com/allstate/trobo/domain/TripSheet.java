package com.allstate.trobo.domain;

import java.util.List;

public class TripSheet {

	private Integer[] vehicleCapcities;

	private List<PickupPoint> pickUpPoints;

	public Integer[] getVehicleCapcities() {
		return vehicleCapcities;
	}

	public void setVehicleCapcities(Integer[] vehicleCapcities) {
		this.vehicleCapcities = vehicleCapcities;
	}

	public List<PickupPoint> getPickUpPoints() {
		return pickUpPoints;
	}

	public void setPickUpPoints(List<PickupPoint> pickUpPoints) {
		this.pickUpPoints = pickUpPoints;
	}

}
