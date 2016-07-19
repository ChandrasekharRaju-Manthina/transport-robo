package com.abcc.trobo.service;

import java.util.List;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;

import com.abcc.trobo.domain.PickupPoint;
import com.abcc.trobo.domain.TripRoute;
import com.abcc.trobo.domain.TripSheet;
import com.abcc.trobo.domain.Vehicle;
import com.abcc.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

public interface TripSheetService {
	VehicleRoutingSolution retrieveOrPrepareTripSheetData(TripSheet tripSheet, boolean create);

	boolean solveRoute(TripSheet tripSheet);

	boolean terminateEarly(TripSheet tripSheet);

	JsonVehicleRoutingSolution generateTripSheet(TripSheet tripSheet,
			VehicleRoutingSolution solution);

	void saveTripSheet(JsonVehicleRoutingSolution tripSheet);

	JsonVehicleRoutingSolution getTripSheet(TripSheet tripSheet);

	List<TripRoute> getTripSheetDataForExport(TripSheet tripSheet);
	
	Vehicle findCab(TripSheet tripSheet, Long empId);
	
	List<PickupPoint> getPickupPointDetails(TripSheet tripSheet);

	boolean isTripSheetExist(TripSheet tripSheet);
	
	JsonVehicleRoutingSolution convertToJsonVehicleRoutingSolution(
			VehicleRoutingSolution solution);
}
