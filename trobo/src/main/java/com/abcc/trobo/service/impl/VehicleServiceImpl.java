package com.abcc.trobo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcc.trobo.dao.VehicleRepository;
import com.abcc.trobo.domain.Vehicle;
import com.abcc.trobo.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

	private VehicleRepository vehicleRepository;

	@Autowired
	public VehicleServiceImpl(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}
	
	@Override
	public Vehicle updateVehicle(Vehicle vehicle) {
		return vehicleRepository.update(vehicle);
	}

	@Override
	public Vehicle addVehicle(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}
	
	@Override
	public void deleteVehicle(Long id) {
		vehicleRepository.delete(id);
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.retrieveAll();
	}

}
