package com.abcc.trobo.service.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.vehiclerouting.domain.Customer;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcc.trobo.dao.AddressRepository;
import com.abcc.trobo.dao.ShiftRepository;
import com.abcc.trobo.dao.TripRouteRepository;
import com.abcc.trobo.dao.TripSheetRepository;
import com.abcc.trobo.domain.Address;
import com.abcc.trobo.domain.Employee;
import com.abcc.trobo.domain.PickupPoint;
import com.abcc.trobo.domain.Shift;
import com.abcc.trobo.domain.TripRoute;
import com.abcc.trobo.domain.TripSheet;
import com.abcc.trobo.helper.TripSheetDataMapper;
import com.abcc.trobo.service.TripSheetService;
import com.abcc.trobo.util.AppConstants;
import com.abcc.trobo.vehiclerouting.VehicleRoutingSolverManager;
import com.abcc.trobo.vehiclerouting.domain.JsonCustomer;
import com.abcc.trobo.vehiclerouting.domain.JsonVehicleRoute;
import com.abcc.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

@Service
public class TripSheetServiceImpl implements TripSheetService {
	
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

	private TripSheetRepository tripSheetRepository;
	
	private ShiftRepository shiftRepository;
	
	private TripRouteRepository tripRouteRepository;
	
	private AddressRepository addressRepository;

	private VehicleRoutingSolverManager solverManager = VehicleRoutingSolverManager
			.getInstance();

	@Autowired
	public TripSheetServiceImpl(TripSheetRepository tripSheetRepository,
			ShiftRepository shiftRepository,
			TripRouteRepository tripRouteRepository, AddressRepository addressRepository) {
		this.tripSheetRepository = tripSheetRepository;
		this.shiftRepository = shiftRepository;
		this.tripRouteRepository = tripRouteRepository;
		this.addressRepository = addressRepository;
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
	
	public com.abcc.trobo.domain.Vehicle findCab(TripSheet tripSheet, Long empId) {
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
		address.setAddressLine("Office");
		address.setLatitude(new BigDecimal(AppConstants.OFFICE_LATITUDE));
		address.setLongitude(new BigDecimal(AppConstants.OFFICE_LONGITUDE));

		PickupPoint pickUpPoint = new PickupPoint();
		pickUpPoint.setAddress(address);
		pickUpPoint.setNumberOfEmployees(0);
		pickUpPoints.add(0, pickUpPoint);
		tripSheet.setPickUpPoints(pickUpPoints);
		tripSheet.setShift(shift);
		
		//sort vehicles
		if(tripSheet.getVehicles() != null) {
			Collections.sort(tripSheet.getVehicles());
		}
		
		tripSheet.setDistanceMatrix(addressRepository.getAddressDistanceMatrix());
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
		
		//get and prepare distance matrix map
//		Address[] addresses = new Address[tripSheet.getPickUpPoints().size()];
//	   	 for(int i = 0; i < tripSheet.getPickUpPoints().size(); i++) {
//	   		 Address address = new Address();
//	   		 address.setLatitude(new BigDecimal(tripSheet.getPickUpPoints().get(i).getAddress().getLatitude()+""));
//	   		 address.setLongitude(new BigDecimal(tripSheet.getPickUpPoints().get(i).getAddress().getLongitude()+""));
//	   		 addresses[i] = address;        		 
//	   	 }
//	   	
//	   	 GoogleMapsHelper helper = new GoogleMapsHelper();
//	   	 DistanceMatrix matrix = helper.findDistanceMatrix(addresses, addresses);
//	   	 Map<Long, Map<Long, DistanceMatrixElement>> distanceMatrix = new HashMap<Long, Map<Long, DistanceMatrixElement>>();
//	   	 for(int i = 0; i < tripSheet.getPickUpPoints().size(); i++) {
//	   		Map<Long, DistanceMatrixElement> addrDistMatrix = new HashMap<Long, DistanceMatrixElement>();
//	   		for(int j = 0; j < tripSheet.getPickUpPoints().size(); j++) {
//	   			DistanceMatrixElement dme = matrix.rows[i].elements[j];
//	   			addrDistMatrix.put(tripSheet.getPickUpPoints().get(j).getAddress().getId(), dme);
//	   		}
//	   		distanceMatrix.put(tripSheet.getPickUpPoints().get(i).getAddress().getId(), addrDistMatrix);
//	   	 }
	   	 
		
		Map<Long, com.abcc.trobo.domain.Vehicle> vehicles = new HashMap<Long, com.abcc.trobo.domain.Vehicle>();
		for(com.abcc.trobo.domain.Vehicle vehicle: tripSheet.getVehicles()) {
			vehicles.put(vehicle.getId(), vehicle);
		}
		
		
		Map<Long, Address> distanceMatrix = addressRepository.getAddressDistanceMatrix();
		
		Iterator<JsonVehicleRoute> iterator = jsonSol.getVehicleRouteList().iterator();		
		while(iterator.hasNext()) {
			JsonVehicleRoute vehicleRoute = iterator.next();
			if(vehicleRoute.getCustomerList().size() == 0) {
				continue;
			}
			
//			long timeInSecond = lastCustomerDistance.duration.inSeconds;
			long totalTime = 0;
			long customerId = 0L;
			

			
			for(int i=0; i< vehicleRoute.getCustomerList().size(); i++) {
				if(distanceMatrix.get(customerId) == null || distanceMatrix.get(customerId).getTimeToEachAddrMap().get(vehicleRoute.getCustomerList().get(i).getId()) == null) {
					totalTime = distanceMatrix.get(vehicleRoute.getCustomerList().get(i).getId()).getTimeToEachAddrMap().get(customerId) + totalTime;
				} else {
					totalTime = distanceMatrix.get(customerId).getTimeToEachAddrMap().get(vehicleRoute.getCustomerList().get(i).getId()) + totalTime;
				}
				
//				totalTime = distanceMatrix.get(customerId).get(customer.getId()).duration.inSeconds + totalTime;
				customerId = vehicleRoute.getCustomerList().get(i).getId();
				
				//if time is more than 5 minutes then provide seperate cab and use next avilable cab
				long timeInSecond = distanceMatrix.get(vehicleRoute.getCustomerList().get(i).getId()).getTimeToEachAddrMap().get(0L);
				if(totalTime - timeInSecond > 660) {
					List<JsonCustomer> sub = new ArrayList<JsonCustomer>(vehicleRoute.getCustomerList().subList(i, vehicleRoute.getCustomerList().size()));
					boolean isReRouteSuccess = false;
					for(JsonVehicleRoute route: jsonSol.getVehicleRouteList()) {
						if(route.getCustomerList().size() == 0 && route.getCapacity() >= sub.size()) {
							isReRouteSuccess = true;
							//remove it from vehicle and add it to new vehicle
							vehicleRoute.getCustomerList().removeAll(sub);
							vehicleRoute.setDemandTotal(vehicleRoute.getCustomerList().size());
							    
							route.getCustomerList().addAll(sub);
							route.setDemandTotal(sub.size());
							break;
						}
					}
					if(!isReRouteSuccess) {
						jsonSol.setIsNotAccurate(true);
						System.out.println("reroute is not success.");
					} else {
						System.out.println("reroute is success.");
					}
					System.out.println(timeInSecond + "-->" + totalTime);
					break;
				}
				
//				calendar.add(Calendar.SECOND, distanceMatrix.get(vehicleRoute.getCustomerList().get(i).getId()).getTimeToEachAddrMap().get(customerId).intValue());
//				vehicleRoute.getCustomerList().get(i).setTime(sdf.format(calendar.getTime()));
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		for(JsonVehicleRoute vehicleRoute: jsonSol.getVehicleRouteList()) {
//			List<Address> pickUpPoints =   new ArrayList<Address>();			
			vehicleRoute.setVehicleNumber(vehicles.get(vehicleRoute.getId()).getVehicleNumber());
			
			if(vehicleRoute.getCustomerList().size() == 0) {
				continue;
			}
			
			long customerId = 0;
			
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
			
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			
			//add or minus additional 15 mins
			if(tripSheet.isDrop()) {
				calendar.add(Calendar.SECOND, 900);
			} else {
				calendar.add(Calendar.SECOND, -900);
			}
			
			for(JsonCustomer customer:vehicleRoute.getCustomerList()) {
				Employee employee = map.get(customer.getId()).poll();
				
				List<Employee> employees= new ArrayList<Employee>();
				employees.add(employee);
				
				customer.setEmployees(employees);
				
				int seconds;
				if(distanceMatrix.get(customerId) == null || distanceMatrix.get(customerId).getTimeToEachAddrMap().get(customer.getId()) == null) {
					seconds = distanceMatrix.get(customer.getId()).getTimeToEachAddrMap().get(customerId).intValue();
				} else {
					seconds = distanceMatrix.get(customerId).getTimeToEachAddrMap().get(customer.getId()).intValue();
				}
				
				if(tripSheet.isDrop()) {
					calendar.add(Calendar.SECOND, seconds);
				} else {
					calendar.add(Calendar.SECOND, -seconds);
				}
				customer.setTime(sdf.format(calendar.getTime()));
				customerId = customer.getId();
				
				
//				Address address = new Address();
//				address.setLatitude(new BigDecimal(customer.getLatitude()+ ""));
//				address.setLongitude(new BigDecimal(customer.getLongitude() + ""));
//				customer.setTime("??:??");
//				pickUpPoints.add(address);
			}
			
//			if(pickUpPoints.size() == 0) {
//				continue;
//			}
			
			//add escort
			JsonCustomer lastAddress = vehicleRoute.getCustomerList().get(vehicleRoute.getCustomerList().size()-1);
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
			
//			Address officeAddress = new Address();
//			officeAddress.setLatitude(new BigDecimal(AppConstants.OFFICE_LATITUDE));
//			officeAddress.setLongitude(new BigDecimal(AppConstants.OFFICE_LONGITUDE));
//			
//			if(tripSheet.isDrop()) {
//				pickUpPoints.add(0,officeAddress);
//			} else {
//				pickUpPoints.add(1,officeAddress);
//			}
//			
//			GoogleMapsHelper helper = new GoogleMapsHelper();
//			
//			Address[] wayPoints = new Address[pickUpPoints.size()];
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//			Date date = null;
//			try {
//				if(tripSheet.isDrop()) {
//					date = sdf.parse(tripSheet.getShift().getEndTime());
//				} else {
//					date = sdf.parse(tripSheet.getShift().getStartTime());
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
//			calendar.setTime(date);   // assigns calendar to given date 
//			int hour = calendar.get(Calendar.HOUR_OF_DAY);
//			int minute = calendar.get(Calendar.MINUTE);
//			
//			Calendar calendar1 = GregorianCalendar.getInstance();
//			calendar1.setTime(tripSheet.getDate());
//			
//			DateTime time = new DateTime(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH)+1, 
//					calendar1.get(Calendar.DAY_OF_MONTH), hour, minute);
//			DirectionsRoute route = helper.findOptimizedRoute(pickUpPoints.toArray(wayPoints), time, !tripSheet.isDrop());
//			
//			DirectionsLeg[] legs = route.legs;
//			
//			if(!tripSheet.isDrop()) {
//				long totalTimeInSeconds = 0;
//				for (int i = 0; i < legs.length; i++) {
//					totalTimeInSeconds = totalTimeInSeconds + legs[i].duration.inSeconds;					
//				}
//				calendar.add(Calendar.SECOND, (int) -totalTimeInSeconds);
//				
//				int count = 0;
//				for (int i = 0; i < vehicleRoute.getCustomerList().size(); i++) {
//					JsonCustomer customer = vehicleRoute.getCustomerList().get(i);					
//					customer.setTime(sdf.format(calendar.getTime()));
//					long duration = legs[count++].duration.inSeconds;
//					calendar.add(Calendar.SECOND, (int) duration);
//				}
//			} else {			
//				for(int i = vehicleRoute.getCustomerList().size() -1; i>=0; i--) {
//					long duration = legs[i].duration.inSeconds;
//					JsonCustomer customer = vehicleRoute.getCustomerList().get(i);
//					calendar.add(Calendar.SECOND, (int) duration);
//					customer.setTime(sdf.format(calendar.getTime()));
//				}
//			}
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
	
	public JsonVehicleRoutingSolution convertToJsonVehicleRoutingSolution(
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
