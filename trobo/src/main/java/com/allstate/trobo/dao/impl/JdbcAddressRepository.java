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
				"insert into Address (address_line, city, state, zip, country, latitude, longitude)"
						+ " values (?, ?, ?, ?, ?, ?, ?)", address.getAddressLine(),
				address.getCity(), address.getState(), address.getZip(),
				address.getCountry(),address.getLatitude(), address.getLongitude());
		return address;
	}

	@Override
	public Address update(Address address) {
		jdbc.update(
				"update Address set address_line=?, city=?, state=?, zip=?, country=?, latitude=?, longitude=? where id=?",
				address.getAddressLine(), address.getCity(),
				address.getState(), address.getZip(), address.getCountry(),
				address.getLatitude(), address.getLongitude(), address.getId());
		return address;
	}

	@Override
	public void delete(Long id) {
		jdbc.update("delete from Address where id=?", id);
	}

	@Override
	public List<Address> retrieveAll() {
		return jdbc
				.query("select id, address_line, city, state, zip, country, latitude, longitude from Address",
						new AddressRowMapper());
	}

	private static class AddressRowMapper implements RowMapper<Address> {
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Address(rs.getLong("id"), rs.getString("address_line"),
					rs.getString("city"), rs.getString("state"),
					rs.getString("zip"), rs.getString("country"),
					rs.getDouble("latitude"), rs.getDouble("longitude"));
		}
	}

	@Override
	public Address retrieveAddressForEmployee(Long empId) {
		
		Object[] parameters = {empId};
		return jdbc
				.queryForObject("select id, address_line, city, state, zip, country, latitude, longitude from Address A,Employee E where E.addressId = A.id and E.id = ?",
						parameters,new AddressRowMapper());
	}
}
