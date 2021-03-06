package com.abcc.trobo.dao;

import java.util.List;

import com.abcc.trobo.domain.Shift;

public interface ShiftRepository {

	Shift save(Shift shift);

	List<Shift> retrieveAll();

	void delete(Long id);

	Shift get(Long id);

	Shift update(Shift shift);

}
