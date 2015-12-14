package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.VehicleRepository;
import com.allstate.trobo.domain.Vehicle;

@Repository
public class JdbcVehicleRepository implements VehicleRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcVehicleRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Vehicle save(Vehicle vehicle) {
		jdbc.update(
				"insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId)"
						+ " values (?, ?, ?, ?)", vehicle.getVehicleNumber(),
				vehicle.getSeats(), vehicle.getTrackingDeviceLink(),
				vehicle.getDriverId());
		return vehicle;
	}

	@Override
	public Vehicle update(Vehicle vehicle) {
		jdbc.update(
				"update Vehicle set vehicleNumber=?, seats=?, trackingDeviceLink=?, driverId=? where id=?",
				vehicle.getVehicleNumber(), vehicle.getSeats(),
				vehicle.getTrackingDeviceLink(), vehicle.getDriverId(),
				vehicle.getId());
		return vehicle;
	}

	@Override
	public void delete(Long id) {
		jdbc.update("delete from Vehicle where id=?", id);
	}

	@Override
	public List<Vehicle> retrieveAll() {
		return jdbc
				.query("select id, vehicleNumber, seats, trackingDeviceLink, driverId from Vehicle",
						new VehicleRowMapper());
	}

	private static class VehicleRowMapper implements RowMapper<Vehicle> {
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Vehicle(rs.getLong("id"), rs.getString("vehicleNumber"),
					rs.getInt("seats"), rs.getString("trackingDeviceLink"),
					rs.getInt("driverId"));
		}
	}

}
