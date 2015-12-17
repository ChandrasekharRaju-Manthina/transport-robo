package com.allstate.trobo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allstate.trobo.dao.TripSheetRepository;
import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.service.TripSheetService;
import com.allstate.trobo.vehiclerouting.VehicleRoutingSolverManager1;

@Service
public class TripSheetServiceImpl implements TripSheetService {

	private TripSheetRepository tripSheetRepository;
	
	private VehicleRoutingSolverManager1 solverManager = VehicleRoutingSolverManager1.getInstance();

	@Autowired
	public TripSheetServiceImpl(TripSheetRepository tripSheetRepository) {
		this.tripSheetRepository = tripSheetRepository;
	}
	
	public VehicleRoutingSolution generateTripSheet(TripSheet tripSheet) {
		List<PickupPoint> pickUpPoints = getPickupPointDetails();
		Address address = new Address();
		address.setId(0L);
		address.setAddressLine("Allstate");
		address.setLatitude(new BigDecimal(12.92539));
		address.setLongitude(new BigDecimal(77.68664));
		
		PickupPoint pickUpPoint = new PickupPoint();
		pickUpPoint.setAddress(address);
		pickUpPoint.setNumberOfEmployees(0);
		pickUpPoints.add(0, pickUpPoint);		
		tripSheet.setPickUpPoints(pickUpPoints);
		VehicleRoutingSolution solution = solverManager.retrieveOrCreateSolution("2", tripSheet);
//		boolean success = solverManager.solve("2");
		return solution;
	}

	public List<PickupPoint> getPickupPointDetails() {
		return tripSheetRepository.getPickUpPointDetails();
	}

}
