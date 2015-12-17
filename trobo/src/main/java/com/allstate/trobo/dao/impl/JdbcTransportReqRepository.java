package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.TransportReqRepository;
import com.allstate.trobo.domain.Shift;
import com.allstate.trobo.domain.TransportReq;

@Repository
public class JdbcTransportReqRepository implements TransportReqRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcTransportReqRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	public List<TransportReq> retrieveAll() {
		return jdbc
				.query("select id, startDate, endDate, employeeId, shiftId, requestType, status  from TransportRequest where startDate<=? and "
						+ "endDate>=?", new Object[] {new Date(), new Date()},
						new ShiftRowMapper());
	}

	private static class ShiftRowMapper implements RowMapper<TransportReq> {
		public TransportReq mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			return new TransportReq(rs.getLong("id"), rs.getDate("startDate"),
					rs.getDate("endDate"), rs.getLong("employeeId"),
					rs.getLong("shiftId"), rs.getString("requestType"),
					rs.getString("status"));
		}
	}

	@Override
	public TransportReq save(TransportReq transportReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public TransportReq update(TransportReq transportReq) {
		// TODO Auto-generated method stub
		return null;
	}

}
