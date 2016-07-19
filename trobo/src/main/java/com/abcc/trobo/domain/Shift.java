package com.abcc.trobo.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Shift {

	private Long id;

	@NotNull
	@Size(max = 10)
	private String startTime;

	@NotNull
	@Size(max = 25)
	private String endTime;

	public Shift() {

	}

	public Shift(Long id, String startTime, String endTime) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
