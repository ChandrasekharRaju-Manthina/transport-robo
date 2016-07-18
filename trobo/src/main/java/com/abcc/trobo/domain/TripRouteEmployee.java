package com.allstate.trobo.domain;

public class TripRouteEmployee {
	private Long id;
	private String routeId;
	private Long empId;
	private Long vehId;
	private String tripTime;
	private String addressLine;
	private Long addressId;
	private String empName;
	private String empSex;

	public TripRouteEmployee(Long id, String routeId, Long empId, Long vehId,
			String tripTime, String addressLine, Long addressId,
			String empName, String empSex) {
		this.id = id;
		this.routeId = routeId;
		this.empId = empId;
		this.vehId = vehId;
		this.tripTime = tripTime;
		this.addressLine = addressLine;
		this.addressId = addressId;
		this.empName = empName;
		this.empSex = empSex;
	}

	public TripRouteEmployee() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getVehId() {
		return vehId;
	}

	public void setVehId(Long vehId) {
		this.vehId = vehId;
	}

	public String getTripTime() {
		return tripTime;
	}

	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpSex() {
		return empSex;
	}

	public void setEmpSex(String empSex) {
		this.empSex = empSex;
	}

}
