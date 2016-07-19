package com.abcc.trobo.util;

import java.math.BigDecimal;

import com.abcc.trobo.domain.Address;

public class AppConstants {

	public static final String OFFICE_LATITUDE = "12.925686137571331";

	public static final String OFFICE_LONGITUDE = "77.6858536183853";

	public static final Address OFFICE_ADDRESS = new Address();

	static {
		OFFICE_ADDRESS.setId(0L);
		OFFICE_ADDRESS.setLatitude(new BigDecimal(AppConstants.OFFICE_LATITUDE));
		OFFICE_ADDRESS.setLongitude(new BigDecimal(AppConstants.OFFICE_LONGITUDE));
	}
}
