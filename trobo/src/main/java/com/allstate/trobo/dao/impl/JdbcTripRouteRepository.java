package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.TripRouteRepository;
import com.allstate.trobo.domain.Driver;
import com.allstate.trobo.domain.TripRoute;
import com.allstate.trobo.domain.TripRouteEmployee;
import com.allstate.trobo.domain.Vehicle;

@Repository
public class JdbcTripRouteRepository implements TripRouteRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcTripRouteRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<TripRoute> save(List<TripRoute> tripRoutes) {
		for (TripRoute route : tripRoutes) {
			jdbc.update(
					"insert into TripRoute (routeId, tripDate, tripType, shiftId,"
							+ "vehicleId, tripDistance, paid)"
							+ " values (?, ?, ?, ?,?,?,?)", route.getRouteId(),
					route.getTripDate(), route.getTripType(),
					route.getShiftId(), route.getVehicleId(),
					route.getTripDistance(), route.getPaid());

			for (TripRouteEmployee employee : route.getEmployees()) {
				jdbc.update(
						"insert into TripRouteEmployee (routeId, empId, vehicleId, tripTime)"
								+ " values (?, ?, ?, ?)",
						employee.getRouteId(), employee.getEmpId(),
						employee.getVehId(), employee.getTripTime());
			}
		}
		return tripRoutes;
	}

	@Override
	public List<TripRoute> get(String tripSheetId) {
		List<TripRoute> tripRoutes = jdbc
				.query("select routeId, tripDate, tripType, shiftId, vehicleId, v.vehicleNumber, tripDistance, "
						+ "paid from TripRoute t, Vehicle v where t.vehicleId=v.id and routeId=?",
						new Object[] { tripSheetId }, new TripRouteMapper());
		for (TripRoute route : tripRoutes) {
			List<TripRouteEmployee> tripRouteEmps = jdbc
					.query("select t.id, routeId, empId, vehicleId, tripTime, e.name, e.sex, a.id as addrId, a.address_line from TripRouteEmployee t, Employee e, Address a "
							+ "where t.empId = e.id and e.addressId = a.id and t.routeId=? and t.vehicleId=?",
							new Object[] { route.getRouteId(),
									route.getVehicleId() },
							new TripRouteEmployeeMapper());
			route.setEmployees(tripRouteEmps);
		}

		return tripRoutes;
	}
	
	@Override
	public Vehicle findCab(String tripSheetId, Long empId) {
		List<Vehicle> vehicles = jdbc
				.query("select v.id, v.vehicleNumber, v.trackingDeviceLink, d.name,"
						+ "d.phoneNumber  from TripRouteEmployee t, Vehicle v, Driver d "
						+ "where t.vehicleId = v.id and v.driverId=d.id and t.empId = ? and t.routeId=?",
						new Object[] { empId, tripSheetId },
						new VehicleMapper());
		
		if(vehicles != null && vehicles.size() !=0)
			return vehicles.get(0);
		
		return null;
	}

	private static class TripRouteMapper implements RowMapper<TripRoute> {
		public TripRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TripRoute(rs.getString("routeId"),
					rs.getDate("tripDate"), rs.getString("tripType"),
					rs.getLong("shiftId"), rs.getLong("vehicleId"),
					rs.getLong("tripDistance"), rs.getString("paid"), rs.getString("vehicleNumber"));
		}
	}
	
	private static class VehicleMapper implements RowMapper<Vehicle> {
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			Vehicle vehicle = new Vehicle();
			vehicle.setId(rs.getLong("id"));
			vehicle.setVehicleNumber(rs.getString("vehicleNumber"));
			vehicle.setTrackingDeviceLink(rs.getString("trackingDeviceLink"));
			
			Driver driver = new Driver();
			driver.setName(rs.getString("name"));
			driver.setPhoneNumber(rs.getString("phoneNumber"));
			vehicle.setDriver(driver);
			return vehicle;
		}
	}

	private static class TripRouteEmployeeMapper implements
			RowMapper<TripRouteEmployee> {
		public TripRouteEmployee mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			return new TripRouteEmployee(rs.getLong("id"),
					rs.getString("routeId"), rs.getLong("empId"),
					rs.getLong("vehicleId"), rs.getString("tripTime"),
					rs.getString("address_line"), rs.getLong("addrId"),
					rs.getString("name"), rs.getString("sex"));
		}
	}

}
