package com.abcc.trobo.service;

import java.util.List;

import com.abcc.trobo.domain.Driver;

public interface DriverService {
	public Driver addDriver(Driver driver);

	public List<Driver> getAllDrivers();

	public void deleteDriver(Long id);

	public Driver updateDriver(Driver driver);
}
