package com.abcc.trobo.service;

import java.util.List;

import com.abcc.trobo.domain.Shift;

public interface ShiftService {
	public Shift addShift(Shift shift);

	public List<Shift> getAllShifts();

	public void deleteShift(Long id);

	public Shift updateShift(Shift shift);
}
