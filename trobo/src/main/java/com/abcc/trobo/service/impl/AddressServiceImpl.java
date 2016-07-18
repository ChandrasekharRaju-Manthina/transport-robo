package com.allstate.trobo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allstate.trobo.dao.AddressRepository;
import com.allstate.trobo.domain.Address;
import com.allstate.trobo.helper.GoogleMapsHelper;
import com.allstate.trobo.service.AddressService;
import com.allstate.trobo.util.AppConstants;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;

@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;

	@Autowired
	public AddressServiceImpl(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
	@Override
	public Address updateAddress(Address address) {
//		GoogleMapsHelper mapHelper = new GoogleMapsHelper();
//		mapHelper.findLatAndLng(address);
		return addressRepository.update(address);
	}

	@Override
	public Address addAddress(Address address) {	
		
		Address[] addressArray = new Address[1];
		addressArray[0] = address;
		
		//get time to other addresses using google maps api
		Address officeAddress = new Address();
		officeAddress.setId(0L);
		officeAddress.setLatitude(new BigDecimal(AppConstants.OFFICE_LATITUDE));
		officeAddress.setLongitude(new BigDecimal(AppConstants.OFFICE_LONGITUDE));		
		
		List<Address> addresses = addressRepository.retrieveAll();
		addresses.add(0, officeAddress);

		GoogleMapsHelper helper = new GoogleMapsHelper();
		DistanceMatrix matrix = helper.findDistanceMatrix(addresses.toArray(new Address[addresses.size()]), addressArray);
		
		StringBuilder timeInSeconds = new StringBuilder();
		StringBuilder distanceInKm = new StringBuilder();
		
		for(int i = 0; i < addresses.size(); i++) {
	   		DistanceMatrixElement dme = matrix.rows[i].elements[0];
	   		timeInSeconds.append(addresses.get(i).getId()).append(":").append(dme.duration.inSeconds).append(",");
	   		//convert meters to km
	   		distanceInKm.append(addresses.get(i).getId()).append(":").append((double)dme.distance.inMeters/1000).append(",");
	   	}
		
		//remove last semicolon
		timeInSeconds.deleteCharAt(timeInSeconds.length()-1);
		distanceInKm.deleteCharAt(distanceInKm.length()-1);
		
		address.setTimeInSeconds(timeInSeconds.toString());
		address.setDistanceInKm(distanceInKm.toString());
		
//		address.setTimeToOfficeInMins(matrix.rows[0].elements[0].duration.inSeconds);

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
