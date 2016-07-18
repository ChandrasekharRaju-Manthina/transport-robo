package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.Employee;
import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripSheet;


public interface TripSheetRepository {
	public List<PickupPoint> getPickUpPointDetails(TripSheet tripSheet);
	public List<Employee> getEmployeeDetails(TripSheet tripSheet, Address address);
}
