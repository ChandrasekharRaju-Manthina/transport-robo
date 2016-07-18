package com.allstate.trobo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allstate.trobo.domain.Address;
import com.allstate.trobo.domain.Driver;
import com.allstate.trobo.domain.Employee;
import com.allstate.trobo.domain.Shift;
import com.allstate.trobo.domain.Vehicle;
import com.allstate.trobo.exception.ApplicationException;
import com.allstate.trobo.service.AddressService;
import com.allstate.trobo.service.DriverService;
import com.allstate.trobo.service.EmployeeService;
import com.allstate.trobo.service.ShiftService;
import com.allstate.trobo.service.VehicleService;

@Controller
public class ViewController {

	final static Logger logger = Logger.getLogger(ViewController.class);

	DriverService driverService;

	ShiftService shiftService;

	VehicleService vehicleService;
	
	EmployeeService employeeService;
	
	AddressService addressService;

	@Autowired
	public ViewController(DriverService driverService,
			ShiftService shiftService, VehicleService vehicleService,
			EmployeeService employeeService, AddressService addressService) {
		this.driverService = driverService;
		this.shiftService = shiftService;
		this.vehicleService = vehicleService;
		this.employeeService = employeeService;
		this.addressService = addressService;
	}

	@RequestMapping({ "/", "home" })
	public String home(HttpServletRequest request,
		    HttpServletResponse response) {		
		logger.debug("Test message. Welcome to home!");
		Employee employee = employeeService.findEmpByName(request.getRemoteUser());
		request.getSession().setAttribute("loggedInUser", employee);
		System.out.println(request.getRemoteUser());
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
	
	@RequestMapping("employeePage")
	public String employeeHome(Model model) {
		return "employeeHome";
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
	
	@RequestMapping("viewTripSheet")
	public String viewTripSheetPage(Model model) {
		List<Shift> shifts = shiftService.getAllShifts();
		model.addAttribute("shifts", shifts);		
		return "viewTripSheet";
	}
	
	@RequestMapping("viewCabPage")
	public String viewCabPage(Model model) {
		List<Shift> shifts = shiftService.getAllShifts();
		model.addAttribute("shifts", shifts);		
		return "viewCabhome";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,
		    HttpServletResponse response) throws ServletException {
		// Invalidate current HTTP session.
	    // Will call JAAS LoginModule logout() method
		response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
	    request.getSession().invalidate();	
	    request.logout();
		return "home";
	}
	
	@RequestMapping("transportReqPage")
	public String transportReqHome(Model model) {
		List<Shift> shifts = shiftService.getAllShifts();
		model.addAttribute("shifts", shifts);
		return "transportReqHome";
	}
	
	@RequestMapping("updateAddressPage")
	public String updateAddressHome(Model model, HttpServletRequest request) {
		List<Address> addresses = addressService.getAllAddresses();
		model.addAttribute("addresses", addresses);
		
		Employee employee = (Employee) request.getSession().getAttribute("loggedInUser");
		model.addAttribute("addressId", employee.getAddressId());
		return "updateAddressHome";
	}
	
}
