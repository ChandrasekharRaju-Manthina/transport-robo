package com.abcc.trobo.dao;

import java.util.List;

import com.abcc.trobo.domain.Driver;

public interface DriverRepository {

	public Driver save(Driver driver);

	public List<Driver> retrieveAll();

	public void delete(Long id);

	Driver update(Driver driver);

}
