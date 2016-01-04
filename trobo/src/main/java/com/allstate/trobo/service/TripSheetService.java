package com.allstate.trobo.service;

import java.util.List;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;

import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripRoute;
import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.domain.Vehicle;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

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
