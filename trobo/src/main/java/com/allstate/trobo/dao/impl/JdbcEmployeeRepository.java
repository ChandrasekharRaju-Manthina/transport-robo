package com.allstate.trobo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.allstate.trobo.dao.EmployeeRepository;
import com.allstate.trobo.domain.Employee;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository {

	private JdbcOperations jdbc;

	@Autowired
	public JdbcEmployeeRepository(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Employee findEmpByName(String empName) {
		List<Employee> employee = jdbc
				.query("select id, name, mangerId, addressId, status, sex from Employee "
						+ "where name=?", new Object[] {empName},
						new EmployeeRowMapper());
		if(employee != null && employee.size()!=0) {
			return employee.get(0);
		} else {
			return null;
		}
	}

	private static class EmployeeRowMapper implements RowMapper<Employee> {
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee emp = new Employee();
			emp.setId(rs.getLong("id"));
			emp.setName(rs.getString("name"));
			emp.setMangerId(rs.getLong("mangerId"));
			emp.setAddressId(rs.getLong("addressId"));
			emp.setStatus(rs.getString("status"));
			emp.setSex(rs.getString("sex"));
			return emp;
		}
	}

}