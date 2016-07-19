package com.abcc.trobo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abcc.trobo.domain.Vehicle;
import com.abcc.trobo.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

	VehicleService vehicleService;

	@Autowired
	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@RequestMapping
	public List<Vehicle> getVehicles() {
		return vehicleService.getAllVehicles();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Vehicle addVehicle(@Valid @RequestBody Vehicle vehicle) {
		return vehicleService.addVehicle(vehicle);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Vehicle updateVehicle(@Valid @RequestBody Vehicle vehicle) {
		return vehicleService.updateVehicle(vehicle);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteVehicle(@PathVariable Long id) {
		vehicleService.deleteVehicle(id);
	}
}