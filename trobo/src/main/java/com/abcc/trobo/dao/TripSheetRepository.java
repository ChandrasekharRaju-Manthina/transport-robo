package com.abcc.trobo.dao;

import java.util.List;

import com.abcc.trobo.domain.Address;
import com.abcc.trobo.domain.Employee;
import com.abcc.trobo.domain.PickupPoint;
import com.abcc.trobo.domain.TripSheet;


public interface TripSheetRepository {
	public List<PickupPoint> getPickUpPointDetails(TripSheet tripSheet);
	public List<Employee> getEmployeeDetails(TripSheet tripSheet, Address address);
}
