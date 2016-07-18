package com.allstate.trobo.service;

import java.util.List;

import com.allstate.trobo.domain.Driver;

public interface DriverService {
	public Driver addDriver(Driver driver);

	public List<Driver> getAllDrivers();

	public void deleteDriver(Long id);

	public Driver updateDriver(Driver driver);
}
