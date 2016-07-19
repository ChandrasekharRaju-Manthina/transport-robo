package com.abcc.trobo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abcc.trobo.domain.Employee;
import com.abcc.trobo.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public Employee addAddress(@Valid @RequestBody Employee employee) {
		 employeeService.addEmployee(employee);
		 return employee;
	}
	
}