package com.allstate.trobo.domain;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {

	private Long id;

	@NotNull
	@Size(min = 5, max = 100)
	private String addressLine;

	@NotNull
	@Size(min = 2, max = 30)
	private String city;

	@NotNull
	@Size(min = 2, max = 30)
	private String state;

	@NotNull
	@Size(min = 2, max = 30)
	private String zip;

	@NotNull
	@Size(min = 2, max = 30)
	private String country;

	private BigDecimal longitude;

	private BigDecimal latitude;

	private String status;

	private String timeInSeconds;

	private String distanceInKm;

	private Map<Long, Long> timeToEachAddrMap;

	private Map<Long, Double> distToEachAddrMap;

	public Address() {

	}

	public Address(Long id, String addressLine1, String city, String state,
			String zip, String country) {
		this.id = id;
		this.addressLine = addressLine1;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public Address(Long id, String addressLine1, String city, String state,
			String zip, String country, BigDecimal latitude,
			BigDecimal longitude, String status, String timeInSeconds,
			String distanceInKm) {
		this.id = id;
		this.addressLine = addressLine1;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
		this.timeInSeconds = timeInSeconds;
		this.distanceInKm = distanceInKm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine1) {
		this.addressLine = addressLine1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getTimeInSeconds() {
		return timeInSeconds;
	}

	public void setTimeInSeconds(String timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
	}

	public String getDistanceInKm() {
		return distanceInKm;
	}

	public void setDistanceInKm(String distanceInKm) {
		this.distanceInKm = distanceInKm;
	}

	public Map<Long, Long> getTimeToEachAddrMap() {
		return timeToEachAddrMap;
	}

	public void setTimeToEachAddrMap(Map<Long, Long> timeToEachAddrMap) {
		this.timeToEachAddrMap = timeToEachAddrMap;
	}

	public Map<Long, Double> getDistToEachAddrMap() {
		return distToEachAddrMap;
	}

	public void setDistToEachAddrMap(Map<Long, Double> distToEachAddrMap) {
		this.distToEachAddrMap = distToEachAddrMap;
	}

}
