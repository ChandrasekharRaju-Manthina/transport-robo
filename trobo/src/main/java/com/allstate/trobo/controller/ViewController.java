package com.allstate.trobo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allstate.trobo.domain.Driver;
import com.allstate.trobo.domain.Shift;
import com.allstate.trobo.domain.Vehicle;
import com.allstate.trobo.exception.ApplicationException;
import com.allstate.trobo.service.DriverService;
import com.allstate.trobo.service.ShiftService;
import com.allstate.trobo.service.VehicleService;

@Controller
public class ViewController {

	final static Logger logger = Logger.getLogger(ViewController.class);

	DriverService driverService;

	ShiftService shiftService;

	VehicleService vehicleService;

	@Autowired
	public ViewController(DriverService driverService,
			ShiftService shiftService, VehicleService vehicleService) {
		this.driverService = driverService;
		this.shiftService = shiftService;
		this.vehicleService = vehicleService;
	}

	@RequestMapping({ "/", "home" })
	public String home(Model model) {
		logger.warn("Test message. Welcome to home!");
		return "home";
	}

	@RequestMapping(value = "exception", method = GET)
	public String exception() {
		throw new ApplicationException("Testing exception");
	}

	@RequestMapping("addressPage")
	public String addressHome(Model model) {
		return "addressHome";
	}

	@RequestMapping("driversPage")
	public String driverHome(Model model) {
		return "driverHome";
	}

	@RequestMapping("vehiclesPage")
	public String vehicleHome(Model model) {
		List<Driver> drivers = driverService.getAllDrivers();
		model.addAttribute("drivers", drivers);
		return "vehicleHome";
	}

	@RequestMapping("shiftPage")
	public String shiftHome(Model model) {
		return "shiftHome";
	}

	@RequestMapping("tripSheetPage")
	public String tripSheetPage(Model model) {
		List<Shift> shifts = shiftService.getAllShifts();
		model.addAttribute("shifts", shifts);
		
		List<Vehicle> vehicles = vehicleService.getAllVehicles();
		model.addAttribute("vehicles", vehicles);
		
		return "tripSheetHome";
	}
}
