package com.allstate.trobo.domain;

import javax.validation.constraints.NotNull;

public class Employee {

	private Long id;

	@NotNull
	private String name;
	
	private String password;
	
	private String role;

	@NotNull
	private Long mangerId;

	private Long addressId;

	@NotNull
	private String status;
	
	private String sex;
	
	private String location;
	
	private String time;
	
	public Employee() {

	}

	public Employee(Long id, String name, Long mangerId, Long addressId,
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

	public Long getMangerId() {
		return mangerId;
	}

	public void setMangerId(Long mangerId) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
