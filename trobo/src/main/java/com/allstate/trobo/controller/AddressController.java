package com.allstate.trobo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allstate.trobo.domain.Address;
import com.allstate.trobo.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	AddressService addressService;

	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@RequestMapping
	public List<Address> getAddresses() {
		return addressService.getAllAddresses();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Address addAddress(@Valid @RequestBody Address address) {
		return addressService.addAddress(address);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Address updateAddress(@Valid @RequestBody Address address) {
		return addressService.updateAddress(address);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteAddress(@PathVariable Long id) {
		System.out.println(id);
		addressService.deleteAddress(id);
	}
}