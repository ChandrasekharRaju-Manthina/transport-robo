package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.Driver;

public interface DriverRepository {

	public Driver save(Driver driver);

	public List<Driver> retrieveAll();

	public void delete(Long id);

	Driver update(Driver driver);

}
