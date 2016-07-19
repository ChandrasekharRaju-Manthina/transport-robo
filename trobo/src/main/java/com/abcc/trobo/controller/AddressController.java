package com.abcc.trobo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abcc.trobo.domain.Address;
import com.abcc.trobo.domain.Employee;
import com.abcc.trobo.service.AddressService;
import com.abcc.trobo.service.EmployeeService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	AddressService addressService;
	
	EmployeeService employeeService;

	@Autowired
	public AddressController(AddressService addressService, EmployeeService employeeService) {
		this.addressService = addressService;
		this.employeeService = employeeService;
	}

	@RequestMapping
	public List<Address> getAddresses() {
		return addressService.getAllAddresses();
	}

	@RequestMapping(method = RequestMethod.GET, value="user/{empId}")
	public Address getAddressForUser(@PathVariable Long empId) {
		return addressService.getAddressForEmployee(empId);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="user/{empId}")
	public Address updateAddressForUser(@PathVariable Long empId, @Valid @RequestBody Address address) {
		return addressService.updateAddressForEmployee(empId,address);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Address addAddress(@Valid @RequestBody Address address) {
		return addressService.addAddress(address);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Address updateAddress(@Valid @RequestBody Address address) {
		return addressService.updateAddress(address);
	}
	
	@RequestMapping(value="employee", method = RequestMethod.POST)
	public Employee updateEmpAddress(@RequestBody Employee employee, HttpServletRequest request) {
		Employee sessionEmpData = (Employee) request.getSession().getAttribute("loggedInUser");
		employee.setId(sessionEmpData.getId());
		employee = employeeService.updateAddress(employee);
		sessionEmpData.setAddressId(employee.getAddressId());
		return employee;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="approveStatus/{addressId}")
	public int updateStatus(@PathVariable Long addressId) {
		return addressService.updateStatus(addressId);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteAddress(@PathVariable Long id) {
		addressService.deleteAddress(id);
	}
}