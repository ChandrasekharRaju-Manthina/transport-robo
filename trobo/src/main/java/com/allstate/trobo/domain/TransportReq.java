package com.allstate.trobo.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransportReq {

	private Long id;

	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	@NotNull
	private Long employeeId;

	@NotNull
	private Long shiftId;

	@NotNull
	@Size(min = 5, max = 25)
	private String requestType;

	@NotNull
	@Size(min = 5, max = 12)
	private String status;

	public TransportReq() {

	}

	public TransportReq(Long id, Date startDate, Date endDate, Long employeeId,
			Long shiftId, String requestType, String status) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employeeId = employeeId;
		this.shiftId = shiftId;
		this.requestType = requestType;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
