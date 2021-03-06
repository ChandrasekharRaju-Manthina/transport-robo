package com.abcc.trobo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcc.trobo.dao.EmployeeRepository;
import com.abcc.trobo.domain.Employee;
import com.abcc.trobo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee findEmpByName(String empName) {
		return employeeRepository.findEmpByName(empName);
	}
	
	@Override
	public Employee updateAddress(Employee employee) {
		return employeeRepository.updateAddress(employee);
	}

	@Override
	public void addEmployee(Employee employee) {
		//TODO: change it
		employee.setMangerId(1L);
		employee.setStatus("A");
		employeeRepository.addEmployee(employee);		
	}

}
