package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.TripSheetRepository;
import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.PickupPoint;

@Repository
public class JdbcTripsheetRepository implements TripSheetRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcTripsheetRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	public List<PickupPoint> getPickUpPointDetails() {
		return jdbc
				.query("select a.id, a.address_line, a.longitude, a.latitude, count(e.id) as empCount from TransportRequest t, Employee e, Address a where t.employeeId = e.id"
						+ " and e.addressId = a.id and t.startDate<=? and t.endDate>=? group by a.id",
						new Object[] { new Date(), new Date() },
						new PickupPointRowMapper());
	}

	private static class PickupPointRowMapper implements RowMapper<PickupPoint> {
		public PickupPoint mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PickupPoint pickUpPoint = new PickupPoint();
			Address address = new Address();
			address.setId(rs.getLong("id"));
			address.setAddressLine(rs.getString("address_line"));
			address.setLongitude(rs.getDouble("longitude"));
			address.setLatitude(rs.getDouble("latitude"));
			pickUpPoint.setAddress(address);
			pickUpPoint.setNumberOfEmployees(rs.getInt("empCount"));
			return pickUpPoint;
		}
	}

}
