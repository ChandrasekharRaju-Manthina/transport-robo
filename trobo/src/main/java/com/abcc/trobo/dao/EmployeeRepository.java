package com.abcc.trobo.dao;

import com.abcc.trobo.domain.Employee;


public interface EmployeeRepository {

	public Employee findEmpByName(String name);

	Employee updateAddress(Employee employee);
	
	void addEmployee(Employee employee);

}
