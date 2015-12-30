package com.allstate.trobo.service;

import com.allstate.trobo.domain.Employee;

public interface EmployeeService {
	public Employee findEmpByName(String empName);

	Employee updateAddress(Employee employee);
}
