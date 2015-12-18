package com.allstate.trobo.controller;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.vehiclerouting.domain.Customer;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.service.TripSheetService;
import com.allstate.trobo.vehiclerouting.domain.JsonCustomer;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoute;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

@RestController
@RequestMapping("/tripSheet")
public class TripSheetController {

	TripSheetService tripSheetService;

	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

	@Autowired
	public TripSheetController(TripSheetService tripSheetService) {
		this.tripSheetService = tripSheetService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public JsonVehicleRoutingSolution genrateTripSheetData(@RequestBody TripSheet tripSheet) {
		tripSheetService.terminateEarly(tripSheet);
		VehicleRoutingSolution solution = tripSheetService.retrieveOrPrepareTripSheetData(tripSheet);
		
		return tripSheetService.generateTripSheet(tripSheet, solution);
	}

	@RequestMapping(method = RequestMethod.POST, value = "solve")
	public boolean solve(@RequestBody TripSheet tripSheet) {
		tripSheetService.retrieveOrPrepareTripSheetData(tripSheet);
		boolean isSuccess = tripSheetService.solveRoute(tripSheet);
		return isSuccess;
	}

	protected JsonVehicleRoutingSolution convertToJsonVehicleRoutingSolution(
			VehicleRoutingSolution solution) {
		JsonVehicleRoutingSolution jsonSolution = new JsonVehicleRoutingSolution();
		jsonSolution.setName(solution.getName());
		List<JsonCustomer> jsonCustomerList = new ArrayList<JsonCustomer>(
				solution.getCustomerList().size());
		for (Customer customer : solution.getCustomerList()) {
			Location customerLocation = customer.getLocation();
			jsonCustomerList.add(new JsonCustomer(customerLocation.getId(), customerLocation.getName(),
					customerLocation.getLatitude(), customerLocation
							.getLongitude(), customer.getDemand()));
		}
		jsonSolution.setCustomerList(jsonCustomerList);
		List<JsonVehicleRoute> jsonVehicleRouteList = new ArrayList<JsonVehicleRoute>(
				solution.getVehicleList().size());
		TangoColorFactory tangoColorFactory = new TangoColorFactory();
		for (Vehicle vehicle : solution.getVehicleList()) {
			JsonVehicleRoute jsonVehicleRoute = new JsonVehicleRoute();
			Location depotLocation = vehicle.getDepot().getLocation();
			jsonVehicleRoute.setDepotLocationName(depotLocation.getName());
			jsonVehicleRoute.setDepotLatitude(depotLocation.getLatitude());
			jsonVehicleRoute.setDepotLongitude(depotLocation.getLongitude());
			jsonVehicleRoute.setCapacity(vehicle.getCapacity());
			Color color = tangoColorFactory.pickColor(vehicle);
			jsonVehicleRoute.setHexColor(String.format("#%02x%02x%02x",
					color.getRed(), color.getGreen(), color.getBlue()));
			Customer customer = vehicle.getNextCustomer();
			int demandTotal = 0;
			List<JsonCustomer> jsonVehicleCustomerList = new ArrayList<JsonCustomer>();
			while (customer != null) {
				Location customerLocation = customer.getLocation();
				demandTotal += customer.getDemand();
				jsonVehicleCustomerList.add(new JsonCustomer(customerLocation.getId(), customerLocation
						.getName(), customerLocation.getLatitude(),
						customerLocation.getLongitude(), customer.getDemand()));
				customer = customer.getNextCustomer();
			}
			jsonVehicleRoute.setDemandTotal(demandTotal);
			jsonVehicleRoute.setCustomerList(jsonVehicleCustomerList);
			jsonVehicleRouteList.add(jsonVehicleRoute);
		}
		jsonSolution.setVehicleRouteList(jsonVehicleRouteList);
		HardSoftLongScore score = solution.getScore();
		jsonSolution.setFeasible(score != null && score.isFeasible());
		jsonSolution.setDistance(solution.getDistanceString(NUMBER_FORMAT));
		return jsonSolution;
	}

	@RequestMapping(value = "sample", method = RequestMethod.GET)
	public TripSheet getPickupPointDetails1() {
		TripSheet tripSheet = new TripSheet();
		Integer[] vehicleCapcities = { 9, 7, 3, 7, 6, 7, 9 };
		tripSheet.setVehicleCapcities(vehicleCapcities);

		Address address = new Address();
		address.setId(0L);
		address.setAddressLine("Allstate");
		address.setLatitude(new BigDecimal(12.92539));
		address.setLongitude(new BigDecimal(77.68664));

		PickupPoint pickUpPoint = new PickupPoint();
		pickUpPoint.setAddress(address);
		pickUpPoint.setNumberOfEmployees(0);

		List<PickupPoint> pickUpPoints = new ArrayList<PickupPoint>();
		pickUpPoints.add(pickUpPoint);
		tripSheet.setPickUpPoints(pickUpPoints);
		tripSheet.setDate(new Date());
		return tripSheet;
	}

}