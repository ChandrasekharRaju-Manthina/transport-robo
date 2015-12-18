package com.allstate.trobo.service.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.vehiclerouting.domain.Customer;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allstate.trobo.dao.TripSheetRepository;
import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.Employee;
import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.service.TripSheetService;
import com.allstate.trobo.vehiclerouting.VehicleRoutingSolverManager1;
import com.allstate.trobo.vehiclerouting.domain.JsonCustomer;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoute;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

@Service
public class TripSheetServiceImpl implements TripSheetService {
	
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

	private TripSheetRepository tripSheetRepository;

	private VehicleRoutingSolverManager1 solverManager = VehicleRoutingSolverManager1
			.getInstance();

	@Autowired
	public TripSheetServiceImpl(TripSheetRepository tripSheetRepository) {
		this.tripSheetRepository = tripSheetRepository;
	}

	public boolean solveRoute(TripSheet tripSheet) {
		return solverManager.solve(tripSheet.getDate().toString()
				+ tripSheet.getShiftId() + tripSheet.isDrop());
	}

	public List<PickupPoint> getPickupPointDetails(TripSheet tripSheet) {
		return tripSheetRepository.getPickUpPointDetails(tripSheet);
	}
	
	public boolean terminateEarly(TripSheet tripSheet) {
        boolean success = solverManager.terminateEarly(tripSheet.getDate().toString()
				+ tripSheet.getShiftId() + tripSheet.isDrop());
        return success;
    }

	public VehicleRoutingSolution retrieveOrPrepareTripSheetData(
			TripSheet tripSheet) {

		List<PickupPoint> pickUpPoints = getPickupPointDetails(tripSheet);
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
		VehicleRoutingSolution solution = solverManager
				.retrieveOrCreateSolution(tripSheet.getDate().toString()
						+ tripSheet.getShiftId() + tripSheet.isDrop(),
						tripSheet);
		return solution;
	}
	
	public JsonVehicleRoutingSolution generateTripSheet(TripSheet tripSheet, VehicleRoutingSolution solution) {
		JsonVehicleRoutingSolution jsonSol = convertToJsonVehicleRoutingSolution(solution);
		
		Map<Long, List<Employee>> map = new HashMap<Long, List<Employee>>();
		for(PickupPoint pickUpPoint: tripSheet.getPickUpPoints()) {
			List<Employee> employees = tripSheetRepository.getEmployeeDetails(tripSheet, pickUpPoint.getAddress());
			map.put(pickUpPoint.getAddress().getId(), employees);
		}
		
		for(JsonVehicleRoute vehicleRoute: jsonSol.getVehicleRouteList()) {
			for(JsonCustomer customer:vehicleRoute.getCustomerList()) {
				customer.setEmployees(map.get(customer.getId()));
			}
		}		
		
		return jsonSol;
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

}
