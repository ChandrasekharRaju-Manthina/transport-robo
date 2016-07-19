package com.abcc.trobo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abcc.trobo.domain.Shift;
import com.abcc.trobo.service.ShiftService;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

	ShiftService shiftService;

	@Autowired
	public ShiftController(ShiftService shiftService) {
		this.shiftService = shiftService;
	}

	@RequestMapping
	public List<Shift> getShifts() {
		return shiftService.getAllShifts();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Shift addShift(@Valid @RequestBody Shift shift) {
		return shiftService.addShift(shift);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Shift updateShift(@Valid @RequestBody Shift shift) {
		return shiftService.updateShift(shift);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteShift(@PathVariable Long id) {
		System.out.println(id);
		shiftService.deleteShift(id);
	}
}