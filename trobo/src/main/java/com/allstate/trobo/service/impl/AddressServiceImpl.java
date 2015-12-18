package com.allstate.trobo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allstate.trobo.dao.AddressRepository;
import com.allstate.trobo.domain.Address;
import com.allstate.trobo.helper.GoogleMapsHelper;
import com.allstate.trobo.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;

	@Autowired
	public AddressServiceImpl(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
	@Override
	public Address updateAddress(Address address) {
		GoogleMapsHelper mapHelper = new GoogleMapsHelper();
		mapHelper.findLatAndLng(address);
		return addressRepository.update(address);
	}

	@Override
	public Address addAddress(Address address) {
		return addressRepository.save(address);
	}
	
	@Override
	public void deleteAddress(Long id) {
		addressRepository.delete(id);
	}

	@Override
	public List<Address> getAllAddresses() {
		return addressRepository.retrieveAll();
	}

	@Override
	public Address getAddressForEmployee(Long empId) {
		return addressRepository.retrieveAddressForEmployee(empId);
	}

	@Override
	public int updateStatus(Long addressId) {
		return addressRepository.updateStatus(addressId);
	}

	@Override
	public Address updateAddressForEmployee(Long empId, Address address) {
		GoogleMapsHelper mapHelper = new GoogleMapsHelper();
		mapHelper.findLatAndLng(address);
		return addressRepository.update(empId, address);
	}
}
