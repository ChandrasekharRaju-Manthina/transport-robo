package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.Address;

public interface AddressRepository {

	public Address save(Address address);

	public List<Address> retrieveAll();

	public void delete(Long id);

	Address update(Address address);
	
	Address retrieveAddressForEmployee(Long empId);

	int updateStatus(Long addressId);

	Address update(Long empId, Address address);

}
