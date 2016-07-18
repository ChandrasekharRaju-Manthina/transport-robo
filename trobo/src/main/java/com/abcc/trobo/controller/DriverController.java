package com.allstate.trobo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allstate.trobo.domain.Driver;
import com.allstate.trobo.service.DriverService;

@RestController
@RequestMapping("/drivers")
public class DriverController {

	DriverService driverService;

	@Autowired
	public DriverController(DriverService driverService) {
		this.driverService = driverService;
	}

	@RequestMapping
	public List<Driver> getDrivers() {
		return driverService.getAllDrivers();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Driver addDriver(@Valid @RequestBody Driver driver) {
		return driverService.addDriver(driver);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Driver updateDriver(@Valid @RequestBody Driver driver) {
		return driverService.updateDriver(driver);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteDriver(@PathVariable Long id) {
		System.out.println(id);
		driverService.deleteDriver(id);
	}
}