package com.abcc.trobo.dao;

import java.util.List;
import java.util.Map;

import com.abcc.trobo.domain.Address;

public interface AddressRepository {

	public Address save(Address address);

	public List<Address> retrieveAll();

	public void delete(Long id);

	Address update(Address address);
	
	Address retrieveAddressForEmployee(Long empId);

	int updateStatus(Long addressId);

	Address update(Long empId, Address address);

	Map<Long, Address> getAddressDistanceMatrix();

}
