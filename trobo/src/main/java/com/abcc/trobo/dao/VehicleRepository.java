package com.abcc.trobo.dao;

import java.util.List;

import com.abcc.trobo.domain.Vehicle;

public interface VehicleRepository {

	public Vehicle save(Vehicle vehicle);

	public List<Vehicle> retrieveAll();

	public void delete(Long id);

	Vehicle update(Vehicle vehicle);

}
