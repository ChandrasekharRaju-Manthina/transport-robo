package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.Shift;

public interface ShiftRepository {

	public Shift save(Shift shift);

	public List<Shift> retrieveAll();

	public void delete(Long id);

	Shift update(Shift shift);

}
