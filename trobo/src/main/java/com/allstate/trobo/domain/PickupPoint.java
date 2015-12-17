package com.allstate.trobo.domain;

public class PickupPoint {

	Address address;
	Integer numberOfEmployees;

	public PickupPoint() {

	}

	public PickupPoint(Address address, Integer numberOfEmployees) {
		this.address = address;
		this.numberOfEmployees = numberOfEmployees;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

}
