package com.allstate.trobo.service;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;

import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

public interface TripSheetService {
	public VehicleRoutingSolution retrieveOrPrepareTripSheetData(TripSheet tripSheet);
	public boolean solveRoute(TripSheet tripSheet);
	public boolean terminateEarly(TripSheet tripSheet);
	public JsonVehicleRoutingSolution generateTripSheet(TripSheet tripSheet,VehicleRoutingSolution solution);
}
