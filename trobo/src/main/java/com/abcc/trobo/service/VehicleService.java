package com.allstate.trobo.service;

import java.util.List;

import com.allstate.trobo.domain.Vehicle;

public interface VehicleService {
	public Vehicle addVehicle(Vehicle vehicle);

	public List<Vehicle> getAllVehicles();

	public void deleteVehicle(Long id);

	public Vehicle updateVehicle(Vehicle vehicle);
}
