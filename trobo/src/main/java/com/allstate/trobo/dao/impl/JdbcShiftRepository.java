package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.ShiftRepository;
import com.allstate.trobo.domain.Shift;
import com.allstate.trobo.domain.Shift;

@Repository
public class JdbcShiftRepository implements ShiftRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcShiftRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Shift save(Shift shift) {
		jdbc.update(
				"insert into Shift (startTime, endTime)" + " values (?, ?)",
				shift.getStartTime(), shift.getEndTime());
		return shift;
	}

	@Override
	public Shift update(Shift shift) {
		jdbc.update("update Shift set startTime=?, endTime=? where id=?",
				shift.getStartTime(), shift.getEndTime(), shift.getId());
		return shift;
	}

	@Override
	public void delete(Long id) {
		jdbc.update("delete from Shift where id=?", id);
	}

	public List<Shift> retrieveAll() {
		return jdbc.query("select id, startTime, endTime from Shift",
				new ShiftRowMapper());
	}

	private static class ShiftRowMapper implements RowMapper<Shift> {
		public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Shift(rs.getLong("id"), rs.getString("startTime"),
					rs.getString("endTime"));
		}
	}
}
