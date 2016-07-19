package com.abcc.trobo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcc.trobo.dao.DriverRepository;
import com.abcc.trobo.domain.Driver;
import com.abcc.trobo.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

	private DriverRepository driverRepository;

	@Autowired
	public DriverServiceImpl(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}
	
	@Override
	public Driver updateDriver(Driver driver) {
		return driverRepository.update(driver);
	}

	@Override
	public Driver addDriver(Driver driver) {
		return driverRepository.save(driver);
	}
	
	@Override
	public void deleteDriver(Long id) {
		driverRepository.delete(id);
	}

	@Override
	public List<Driver> getAllDrivers() {
		return driverRepository.retrieveAll();
	}

}
