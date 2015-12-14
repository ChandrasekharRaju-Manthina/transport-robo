package com.allstate.trobo.domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Driver {
	
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 50)
	private String name;
	
	@NotNull
	@Size(min = 5, max = 25)
	private String licenseNumber;
	
	@NotNull
	@Size(min = 5, max = 12)
	private String phoneNumber;
	
	@NotNull
	@Digits(integer=2, fraction = 0)
	@Min(1)
	@Max(60)
	private Integer yearsOfExperience;

	public Driver() {

	}

	public Driver(Long id, String name, String licenseNumber,
			String phoneNumber, int yearsOfExperience) {
		this.id = id;
		this.name = name;
		this.licenseNumber = licenseNumber;
		this.phoneNumber = phoneNumber;
		this.yearsOfExperience = yearsOfExperience;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

}
