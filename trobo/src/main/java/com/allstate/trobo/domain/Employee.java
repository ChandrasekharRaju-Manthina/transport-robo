package com.allstate.trobo.domain;

import javax.validation.constraints.NotNull;

public class Employee {

	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String mangerId;

	private Long addressId;

	@NotNull
	private String status;
	
	private String sex;

	public Employee() {

	}

	public Employee(Long id, String name, String mangerId, Long addressId,
			String status, String sex) {
		this.id = id;
		this.name = name;
		this.mangerId = mangerId;
		this.addressId = addressId;
		this.status = status;
		this.sex = sex;
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

	public String getMangerId() {
		return mangerId;
	}

	public void setMangerId(String mangerId) {
		this.mangerId = mangerId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
