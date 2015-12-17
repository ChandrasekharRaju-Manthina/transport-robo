package com.allstate.trobo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allstate.trobo.dao.ShiftRepository;
import com.allstate.trobo.domain.Shift;
import com.allstate.trobo.service.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService {

	private ShiftRepository shiftRepository;

	@Autowired
	public ShiftServiceImpl(ShiftRepository shiftRepository) {
		this.shiftRepository = shiftRepository;
	}
	
	@Override
	public Shift updateShift(Shift shift) {
		return shiftRepository.update(shift);
	}

	@Override
	public Shift addShift(Shift shift) {
		return shiftRepository.save(shift);
	}
	
	@Override
	public void deleteShift(Long id) {
		shiftRepository.delete(id);
	}

	@Override
	public List<Shift> getAllShifts() {
		return shiftRepository.retrieveAll();
	}

}
