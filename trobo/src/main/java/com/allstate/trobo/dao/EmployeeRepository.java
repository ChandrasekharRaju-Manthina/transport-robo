package com.allstate.trobo.dao;

import com.allstate.trobo.domain.Employee;

public interface EmployeeRepository {

	public Employee findEmpByName(String name);

	Employee updateAddress(Employee employee);
	
	void addEmployee(Employee employee);

}
