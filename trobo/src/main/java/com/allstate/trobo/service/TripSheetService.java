package com.allstate.trobo.service;

import java.util.List;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;

import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripSheet;

public interface TripSheetService {
	public List<PickupPoint> getPickupPointDetails();
	public VehicleRoutingSolution generateTripSheet(TripSheet tripSheet);
}
