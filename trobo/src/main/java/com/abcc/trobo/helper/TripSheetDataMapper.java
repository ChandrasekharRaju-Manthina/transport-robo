package com.abcc.trobo.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abcc.trobo.domain.Employee;
import com.abcc.trobo.domain.TripRoute;
import com.abcc.trobo.domain.TripRouteEmployee;
import com.abcc.trobo.domain.TripSheet;
import com.abcc.trobo.vehiclerouting.domain.JsonCustomer;
import com.abcc.trobo.vehiclerouting.domain.JsonVehicleRoute;
import com.abcc.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

public class TripSheetDataMapper {

	public static JsonVehicleRoutingSolution mapRoutesToTripSheet(
			List<TripRoute> tripRoutes, TripSheet tripSheet) {

		JsonVehicleRoutingSolution jsonSolution = new JsonVehicleRoutingSolution();
		jsonSolution.setTripDate(tripSheet.getDate());
		jsonSolution.setShiftId(tripSheet.getShiftId());
		if (tripSheet.isDrop()) {
			jsonSolution.setTripType("D");
		} else {
			jsonSolution.setTripType("P");
		}

		List<JsonVehicleRoute> jsonVehicleRouteList = new ArrayList<JsonVehicleRoute>(
				tripRoutes.size());
		for (TripRoute vehicle : tripRoutes) {
			JsonVehicleRoute jsonVehicleRoute = new JsonVehicleRoute();
			jsonVehicleRoute.setId(vehicle.getVehicleId());
			jsonVehicleRoute.setVehicleNumber(vehicle.getVehicleNumber());

			List<JsonCustomer> jsonVehicleCustomerList = new ArrayList<JsonCustomer>();

			Map<Long, List<TripRouteEmployee>> map = new HashMap<Long, List<TripRouteEmployee>>();
			for (TripRouteEmployee employee : vehicle.getEmployees()) {
				if (map.containsKey(employee.getAddressId())) {
					map.get(employee.getAddressId()).add(employee);
				} else {
					List<TripRouteEmployee> tripRouteEmps = new ArrayList<TripRouteEmployee>();
					tripRouteEmps.add(employee);
					map.put(employee.getAddressId(), tripRouteEmps);
				}
			}

			for (Map.Entry<Long, List<TripRouteEmployee>> addresses : map
					.entrySet()) {
				JsonCustomer jsonCustomer = new JsonCustomer(
						addresses.getKey(), addresses.getValue().get(0)
								.getAddressLine(), 0, 0, addresses.getValue()
								.size());
				
				jsonCustomer.setTime(addresses.getValue().get(0).getTripTime());
				List<Employee> employees = new ArrayList<Employee>();

				for (TripRouteEmployee routeEmp : addresses.getValue()) {
					Employee emp = new Employee();
					emp.setId(routeEmp.getEmpId());
					emp.setName(routeEmp.getEmpName());
					emp.setSex(routeEmp.getEmpSex());
					employees.add(emp);
				}

				jsonCustomer.setEmployees(employees);
				jsonVehicleCustomerList.add(jsonCustomer);
			}

			jsonVehicleRoute.setCustomerList(jsonVehicleCustomerList);
			jsonVehicleRouteList.add(jsonVehicleRoute);
		}
		jsonSolution.setVehicleRouteList(jsonVehicleRouteList);
		return jsonSolution;

	}

	public static List<TripRoute> mapTripSheetToRoute(
			JsonVehicleRoutingSolution solution) {
		List<TripRoute> routes = new ArrayList<TripRoute>();
		String routeId = solution.getTripDate() + "" + solution.getShiftId()
				+ solution.getTripType();
		for (JsonVehicleRoute jsonRoute : solution.getVehicleRouteList()) {
			TripRoute route = new TripRoute();

			route.setRouteId(routeId);
			route.setTripDate(solution.getTripDate());
			route.setTripType(solution.getTripType());
			route.setShiftId(solution.getShiftId());
			route.setVehicleId(jsonRoute.getId());
			List<TripRouteEmployee> tripRouteEmpList = new ArrayList<TripRouteEmployee>();

			for (JsonCustomer customer : jsonRoute.getCustomerList()) {
				for (Employee emp : customer.getEmployees()) {
					TripRouteEmployee tripRouteEmp = new TripRouteEmployee();
					tripRouteEmp.setRouteId(routeId);
					tripRouteEmp.setEmpId(emp.getId());
					tripRouteEmp.setVehId(jsonRoute.getId());
					tripRouteEmp.setTripTime(customer.getTime());
					tripRouteEmpList.add(tripRouteEmp);
				}
			}
			route.setEmployees(tripRouteEmpList);
			routes.add(route);
		}
		return routes;
	}
}
