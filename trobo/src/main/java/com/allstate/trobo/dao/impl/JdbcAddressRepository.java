package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.AddressRepository;
import com.allstate.trobo.domain.Address;

@Repository
public class JdbcAddressRepository implements AddressRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcAddressRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Address save(Address address) {
		jdbc.update(
				"insert into Address (address_line, city, state, zip, country, latitude, longitude, status)"
						+ " values (?, ?, ?, ?, ?, ?, ?, 'Pending')", address.getAddressLine(),
				address.getCity(), address.getState(), address.getZip(),
				address.getCountry(),address.getLatitude(), address.getLongitude());
		return address;
	}

	@Override
	public Address update(Address address) {
		jdbc.update(
				"update Address set address_line=?, city=?, state=?, zip=?, country=?, latitude=?, longitude=?, status='Pending' where id=?",
				address.getAddressLine(), address.getCity(),
				address.getState(), address.getZip(), address.getCountry(),
				address.getLatitude(), address.getLongitude(), address.getId());
		return address;
	}

	@Override
	public int updateStatus(Long addressId) {
		int result = jdbc.update(
				"update Address set status='Approved' where id=?", addressId);
		return result;
	}
	
	@Override
	public void delete(Long id) {
		jdbc.update("delete from Address where id=?", id);
	}

	@Override
	public List<Address> retrieveAll() {
		return jdbc
				.query("select id, address_line, city, state, zip, country, latitude, longitude, status from Address",
						new AddressRowMapper());
	}

	private static class AddressRowMapper implements RowMapper<Address> {
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Address(rs.getLong("id"), rs.getString("address_line"),
					rs.getString("city"), rs.getString("state"),
					rs.getString("zip"), rs.getString("country"),
					rs.getBigDecimal("latitude"), rs.getBigDecimal("longitude"), rs.getString("status"));
		}
	}

	@Override
	public Address retrieveAddressForEmployee(Long empId) {
		
		Object[] parameters = {empId};
		return jdbc
				.queryForObject("select A.id, address_line, city, state, zip, country, latitude, longitude, A.status from Address A,Employee E where E.addressId = A.id and E.id = ?",
						parameters,new AddressRowMapper());
	}

	@Override
	public Address update(Long empId, Address address) {
		jdbc.update(
				"insert into Address (address_line, city, state, zip, country, latitude, longitude, status)"
						+ " values (?, ?, ?, ?, ?, ?, ?, 'Pending')", address.getAddressLine(),
				address.getCity(), address.getState(), address.getZip(),
				address.getCountry(),address.getLatitude(), address.getLongitude());
		
		Object[] parameters = {address.getLatitude(), address.getLongitude()};
		Address newAddress = jdbc.queryForObject("select id from Address where latitude=? and longitude=?",parameters,
						new AddressRowMapper());
		
		jdbc.update(
				"update Employee set addressId = ? where id = ?", newAddress.getId());
		return newAddress;
	}
}
