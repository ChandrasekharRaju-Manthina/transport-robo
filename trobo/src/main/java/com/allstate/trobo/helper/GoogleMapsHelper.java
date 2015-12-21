package com.allstate.trobo.helper;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.allstate.trobo.domain.Address;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;

public class GoogleMapsHelper {

	private GeoApiContext context;
	
	public GoogleMapsHelper() {
		
		String apiKey = "AIzaSyBO3vhXOqMBquP4h11rDb1l7sqKi-zyXZc";
		context = new GeoApiContext();
		if (apiKey != null && !apiKey.equalsIgnoreCase("")) {
		      context.setApiKey(apiKey)
		      		.setQueryRateLimit(10)
		      		.setConnectTimeout(10, TimeUnit.SECONDS)
		      		.setReadTimeout(10, TimeUnit.SECONDS)
		        	.setWriteTimeout(10, TimeUnit.SECONDS);
		}else {
			throw new IllegalArgumentException("No credentials found! Set the API_KEY or CLIENT_ID and "
			          + "CLIENT_SECRET environment variables to run tests requiring authentication.");
		}       
	}
	
	public void findLatAndLng(Address aAddress) {
		
		try {
			GeocodingResult[] results = GeocodingApi.newRequest(context).address(aAddress.getAddressLine() +", "+ aAddress.getCity() +", "+ aAddress.getZip()).await();
			aAddress.setLatitude(new BigDecimal(results[0].geometry.location.lat));
			aAddress.setLongitude(new BigDecimal(results[0].geometry.location.lng));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DirectionsRoute findOptimizedRoute(Address[] wayPoints, DateTime time, boolean flag) {
		
		//ReadableInstant arrivalTime = new DateTime(2015, 1, 1, 19, 0, DateTimeZone.UTC);
		int size = wayPoints.length;
		String[] wayPointsString = new String[size-2];
		for(int i=1;i<size-1;i++) {
			wayPointsString[i-1] = wayPoints[i].getLatitude()+","+wayPoints[i].getLongitude();
		}
		try {
			DirectionsApiRequest request = DirectionsApi.newRequest(context)
			        .origin(wayPoints[0].getLatitude()+","+wayPoints[0].getLongitude())
			        .destination(wayPoints[size-1].getLatitude()+","+wayPoints[size-1].getLongitude())
			        .optimizeWaypoints(true)
			        .waypoints(wayPointsString);
			if(flag) {
				//use it for pick up
				request.arrivalTime(time);
			} else {
				//use it for drop
				request.departureTime(time);
			}
			DirectionsRoute[] result = request.await();
		
			return result[0];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 		
	}
	
	public DistanceMatrix findDistanceMatrix(Address[] origins, Address[] destinations) {
		
		String[] originsArray = new String[origins.length];
		String[] destinationsArray = new String[destinations.length];
		DistanceMatrix matrix = null;
		for(int i =0;i<origins.length;i++) {
			originsArray[i] = origins[i].getLatitude()+","+origins[i].getLongitude();
		}
		for(int i =0;i<destinations.length;i++) {
			destinationsArray[i] = destinations[i].getLatitude()+","+destinations[i].getLongitude();
		}
		try {
			matrix =
			        DistanceMatrixApi.getDistanceMatrix(context, originsArray, destinationsArray).await();
			return matrix;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return matrix;
		}
	}
}
