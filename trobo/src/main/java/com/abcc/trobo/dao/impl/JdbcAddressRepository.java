package com.abcc.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.abcc.trobo.dao.AddressRepository;
import com.abcc.trobo.domain.Address;

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
				"insert into Address (address_line, city, state, zip, country, latitude, longitude, status, timeInSeconds, distanceInKm)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", address.getAddressLine(),
				address.getCity(), address.getState(), address.getZip(),
				address.getCountry(),address.getLatitude(), address.getLongitude(), "A", address.getTimeInSeconds(), address.getDistanceInKm());
		return address;
	}

	@Override
	public Address update(Address address) {
		jdbc.update(
				"update Address set address_line=?, city=?, state=?, zip=?, country=?, latitude=?, longitude=?, status='A', timeInSeconds=?, distanceInKm=? where id=?",
				address.getAddressLine(), address.getCity(),
				address.getState(), address.getZip(), address.getCountry(),
				address.getLatitude(), address.getLongitude(), address.getTimeInSeconds(), address.getDistanceInKm(), address.getId());
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
				.query("select id, address_line, city, state, zip, country, latitude, longitude, status, timeInSeconds, distanceInKm from Address",
						new AddressRowMapper());
	}
	
	@Override
	public Map<Long, Address> getAddressDistanceMatrix() {
		List<Address> addresses = retrieveAll();
		Map<Long, Address> map = new HashMap<Long, Address>();
		
		for(Address address: addresses) {
			String[] timeToEachAddress = address.getTimeInSeconds().split(",");
			Map<Long, Long> timeToEachAddrMap = new HashMap<Long, Long>();
			for(String s: timeToEachAddress) {
				timeToEachAddrMap.put(Long.parseLong(s.split(":")[0]), Long.parseLong(s.split(":")[1]));
			}
			timeToEachAddrMap.put(address.getId(), 0L);
			address.setTimeToEachAddrMap(timeToEachAddrMap);
			
			String[] distToEachAddress = address.getDistanceInKm().split(",");
			Map<Long, Double> distToEachAddrMap = new HashMap<Long, Double>();
			for(String s: distToEachAddress) {
				distToEachAddrMap.put(Long.parseLong(s.split(":")[0]), Double.parseDouble(s.split(":")[1]));
			}
			distToEachAddrMap.put(address.getId(), 0.0);
			address.setDistToEachAddrMap(distToEachAddrMap);
			map.put(address.getId(), address);
		}
		
//		addresses.set(0, AppConstants.OFFICE_ADDRESS);
//		
//		for(int i=0; i< addresses.size();i++) {
//			HashMap<Long, Address> distanceAndTimeMap = new HashMap<Long, Address>();
//			
//			for(int j=0;j<i;j++) {
//				distanceAndTimeMap.put(addresses.get(j).getId(), addresses.get(j));
//			}
//			
////			map.put(address.getId(), distanceAndTimeMap);
//		}
//		
		return map;
	}

	private static class AddressRowMapper implements RowMapper<Address> {
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Address(rs.getLong("id"), rs.getString("address_line"),
					rs.getString("city"), rs.getString("state"),
					rs.getString("zip"), rs.getString("country"),
					rs.getBigDecimal("latitude"), rs.getBigDecimal("longitude"), rs.getString("status"),
					rs.getString("timeInSeconds"), rs.getString("distanceInKm"));
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
