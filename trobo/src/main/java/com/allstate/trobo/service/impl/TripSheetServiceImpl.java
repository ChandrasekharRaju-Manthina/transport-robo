package com.allstate.trobo.service.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.joda.time.DateTime;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.vehiclerouting.domain.Customer;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allstate.trobo.dao.ShiftRepository;
import com.allstate.trobo.dao.TripRouteRepository;
import com.allstate.trobo.dao.TripSheetRepository;
import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.Employee;
import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.Shift;
import com.allstate.trobo.domain.TripRoute;
import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.helper.GoogleMapsHelper;
import com.allstate.trobo.helper.TripSheetDataMapper;
import com.allstate.trobo.service.TripSheetService;
import com.allstate.trobo.vehiclerouting.VehicleRoutingSolverManager1;
import com.allstate.trobo.vehiclerouting.domain.JsonCustomer;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoute;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;

@Service
public class TripSheetServiceImpl implements TripSheetService {
	
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

	private TripSheetRepository tripSheetRepository;
	
	private ShiftRepository shiftRepository;
	
	private TripRouteRepository tripRouteRepository;

	private VehicleRoutingSolverManager1 solverManager = VehicleRoutingSolverManager1
			.getInstance();

	@Autowired
	public TripSheetServiceImpl(TripSheetRepository tripSheetRepository,
			ShiftRepository shiftRepository,
			TripRouteRepository tripRouteRepository) {
		this.tripSheetRepository = tripSheetRepository;
		this.shiftRepository = shiftRepository;
		this.tripRouteRepository = tripRouteRepository;
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
	
	public com.allstate.trobo.domain.Vehicle findCab(TripSheet tripSheet, Long empId) {
		String tripSheetId = tripSheet.getDate().toString()
				+ tripSheet.getShiftId() + (tripSheet.isDrop() ? "D" : "P");
		return tripRouteRepository.findCab(tripSheetId, empId);
	}

	public VehicleRoutingSolution retrieveOrPrepareTripSheetData(
			TripSheet tripSheet, boolean create) {

		List<PickupPoint> pickUpPoints = getPickupPointDetails(tripSheet);
		Shift shift = shiftRepository.get(tripSheet.getShiftId());
		
		Address address = new Address();
		address.setId(0L);
		address.setAddressLine("Allstate");
		address.setLatitude(new BigDecimal(12.9254186));
		address.setLongitude(new BigDecimal(77.6861396));

		PickupPoint pickUpPoint = new PickupPoint();
		pickUpPoint.setAddress(address);
		pickUpPoint.setNumberOfEmployees(0);
		pickUpPoints.add(0, pickUpPoint);
		tripSheet.setPickUpPoints(pickUpPoints);
		tripSheet.setShift(shift);
		VehicleRoutingSolution solution;
		if(create) {
			solution = solverManager.createSolution(tripSheet.getDate().toString()
							+ tripSheet.getShiftId() + tripSheet.isDrop(),
							tripSheet);
		} else {
			solution = solverManager
					.retrieveOrCreateSolution(tripSheet.getDate().toString()
							+ tripSheet.getShiftId() + tripSheet.isDrop(),
							tripSheet);
		}
		return solution;
	}
	
	public JsonVehicleRoutingSolution generateTripSheet(TripSheet tripSheet, VehicleRoutingSolution solution) {
		JsonVehicleRoutingSolution jsonSol = convertToJsonVehicleRoutingSolution(solution);
		jsonSol.setTripDate(tripSheet.getDate());
		jsonSol.setShiftId(tripSheet.getShiftId());
		if(tripSheet.isDrop()) {
			jsonSol.setTripType("D");
		} else {
			jsonSol.setTripType("P");
		}
		
		Map<Long, Queue<Employee>> map = new HashMap<Long, Queue<Employee>>();
		for(PickupPoint pickUpPoint: tripSheet.getPickUpPoints()) {
			List<Employee> employees = tripSheetRepository.getEmployeeDetails(tripSheet, pickUpPoint.getAddress());
			Queue<Employee> queue = new LinkedList<Employee>(employees);
			map.put(pickUpPoint.getAddress().getId(), queue);
		}
		
		Map<Long, com.allstate.trobo.domain.Vehicle> vehicles = new HashMap<Long, com.allstate.trobo.domain.Vehicle>();
		for(com.allstate.trobo.domain.Vehicle vehicle: tripSheet.getVehicles()) {
			vehicles.put(vehicle.getId(), vehicle);
		}
		
		for(JsonVehicleRoute vehicleRoute: jsonSol.getVehicleRouteList()) {
			List<Address> pickUpPoints =   new ArrayList<Address>();			
			vehicleRoute.setVehicleNumber(vehicles.get(vehicleRoute.getId()).getVehicleNumber());
			for(JsonCustomer customer:vehicleRoute.getCustomerList()) {
				Employee employee = map.get(customer.getId()).poll();
				
				List<Employee> employees= new ArrayList<Employee>();
				employees.add(employee);
				
				customer.setEmployees(employees);
				Address address = new Address();
				address.setLatitude(new BigDecimal(customer.getLatitude()).round(new MathContext(14, RoundingMode.HALF_UP)));
				address.setLongitude(new BigDecimal(customer.getLongitude()).round(new MathContext(14, RoundingMode.HALF_UP)));
				customer.setTime("??:??");
				pickUpPoints.add(address);
			}
			
			if(pickUpPoints.size() == 0) {
				continue;
			}
			
			//add escort
			JsonCustomer lastAddress = vehicleRoute.getCustomerList().get(0);
			List<Employee> employees = lastAddress.getEmployees();
			boolean isFemaleEmp = true;
			for(Employee emp: employees) {
				if(("M").equalsIgnoreCase(emp.getSex())) {
					isFemaleEmp = false;
				}
			}
			
			if(isFemaleEmp && vehicleRoute.getCapacity() > vehicleRoute.getDemandTotal()) {
				Employee emp = new Employee();
				emp.setId(49L);
				emp.setName("ESCORT");
				emp.setSex("M");
				emp.setAddressId(lastAddress.getId());
				employees.add(emp);
			}				
			
			Address officeAddress = new Address();
			officeAddress.setLatitude(new BigDecimal("12.92539"));
			officeAddress.setLongitude(new BigDecimal("77.68664"));
			
			if(tripSheet.isDrop()) {
				pickUpPoints.add(0,officeAddress);
			} else {
				pickUpPoints.add(1,officeAddress);
			}
			
			GoogleMapsHelper helper = new GoogleMapsHelper();
			
			Address[] wayPoints = new Address[pickUpPoints.size()];
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Date date = null;
			try {
				if(tripSheet.isDrop()) {
					date = sdf.parse(tripSheet.getShift().getEndTime());
				} else {
					date = sdf.parse(tripSheet.getShift().getStartTime());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
			calendar.setTime(date);   // assigns calendar to given date 
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			
			Calendar calendar1 = GregorianCalendar.getInstance();
			calendar1.setTime(tripSheet.getDate());
			
			DateTime time = new DateTime(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH)+1, 
					calendar1.get(Calendar.DAY_OF_MONTH), hour, minute);
			DirectionsRoute route = helper.findOptimizedRoute(pickUpPoints.toArray(wayPoints), time, !tripSheet.isDrop());
			
			DirectionsLeg[] legs = route.legs;
			
			if(!tripSheet.isDrop()) {
				long totalTimeInSeconds = 0;
				for (int i = 0; i < legs.length; i++) {
					totalTimeInSeconds = totalTimeInSeconds + legs[i].duration.inSeconds;					
				}
				calendar.add(Calendar.SECOND, (int) -totalTimeInSeconds);
				
				int count = 0;
				for (int i = 0; i < vehicleRoute.getCustomerList().size(); i++) {
					JsonCustomer customer = vehicleRoute.getCustomerList().get(i);					
					customer.setTime(sdf.format(calendar.getTime()));
					long duration = legs[count++].duration.inSeconds;
					calendar.add(Calendar.SECOND, (int) duration);
				}
			} else {			
				for(int i = vehicleRoute.getCustomerList().size() -1; i>=0; i--) {
					long duration = legs[i].duration.inSeconds;
					JsonCustomer customer = vehicleRoute.getCustomerList().get(i);
					calendar.add(Calendar.SECOND, (int) duration);
					customer.setTime(sdf.format(calendar.getTime()));
				}
			}
		}
		
		
		return jsonSol;
	}
	
	@Override
	public void saveTripSheet(JsonVehicleRoutingSolution tripSheet) {
		List<TripRoute> tripRoutes = TripSheetDataMapper.mapTripSheetToRoute(tripSheet);
		tripRouteRepository.save(tripRoutes);	
	}
	
	@Override
	public boolean isTripSheetExist(TripSheet tripSheet) {
		List<TripRoute> tripRoutes = tripRouteRepository.get(tripSheet.getDate()
				+ ""
				+ tripSheet.getShiftId()
				+ (tripSheet.isDrop() ? "D" : "P"));
		if(tripRoutes == null || tripRoutes.size() ==0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public JsonVehicleRoutingSolution getTripSheet(TripSheet tripSheet) {
		List<TripRoute> tripRoutes = tripRouteRepository.get(tripSheet.getDate()
				+ ""
				+ tripSheet.getShiftId()
				+ (tripSheet.isDrop() ? "D" : "P"));
		return TripSheetDataMapper.mapRoutesToTripSheet(tripRoutes, tripSheet);
	}
	
	@Override
	public List<TripRoute> getTripSheetDataForExport(TripSheet tripSheet) {
		List<TripRoute> tripRoutes = tripRouteRepository.get(tripSheet.getDate()
				+ ""
				+ tripSheet.getShiftId()
				+ (tripSheet.isDrop() ? "D" : "P"));
		return tripRoutes;
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
			jsonVehicleRoute.setId(vehicle.getId());
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
