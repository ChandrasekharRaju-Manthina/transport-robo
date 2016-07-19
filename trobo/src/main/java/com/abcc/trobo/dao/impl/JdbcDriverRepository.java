package com.abcc.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.abcc.trobo.dao.DriverRepository;
import com.abcc.trobo.domain.Driver;

@Repository
public class JdbcDriverRepository implements DriverRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcDriverRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Driver save(Driver driver) {
		jdbc.update(
				"insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience)"
						+ " values (?, ?, ?, ?)", driver.getName(),
				driver.getLicenseNumber(), driver.getPhoneNumber(),
				driver.getYearsOfExperience());
		return driver;
	}

	@Override
	public Driver update(Driver driver) {
		jdbc.update(
				"update Driver set name=?, licenseNumber=?, phoneNumber=?, yearsOfExperience=? where id=?",
				driver.getName(), driver.getLicenseNumber(), driver.getPhoneNumber(),
				driver.getYearsOfExperience(), driver.getId());
		return driver;
	}

	@Override
	public void delete(Long id) {
		jdbc.update("delete from Driver where id=?", id);
	}

	@Override
	public List<Driver> retrieveAll() {
		return jdbc
				.query("select id, name, licenseNumber, phoneNumber, yearsOfExperience from Driver",
						new DriverRowMapper());
	}

	private static class DriverRowMapper implements RowMapper<Driver> {
		public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Driver(rs.getLong("id"), rs.getString("name"),
					rs.getString("licenseNumber"), rs.getString("phoneNumber"),
					rs.getInt("yearsOfExperience"));
		}
	}

}
