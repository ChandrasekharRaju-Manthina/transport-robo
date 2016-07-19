package com.abcc.trobo.service;

import com.abcc.trobo.domain.Employee;


public interface EmployeeService {
	public Employee findEmpByName(String empName);

	Employee updateAddress(Employee employee);
	
	public void addEmployee(Employee employee);
}
