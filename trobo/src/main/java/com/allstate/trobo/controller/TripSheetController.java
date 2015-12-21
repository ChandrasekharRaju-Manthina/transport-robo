package com.allstate.trobo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.allstate.trobo.domain.BaseDTO;
import com.allstate.trobo.domain.Employee;
import com.allstate.trobo.domain.PickupPoint;
import com.allstate.trobo.domain.TripRoute;
import com.allstate.trobo.domain.TripSheet;
import com.allstate.trobo.domain.Vehicle;
import com.allstate.trobo.service.TripSheetService;
import com.allstate.trobo.vehiclerouting.domain.JsonVehicleRoutingSolution;

@RestController
@RequestMapping("/tripSheet")
public class TripSheetController {

	TripSheetService tripSheetService;

	@Autowired
	public TripSheetController(TripSheetService tripSheetService) {
		this.tripSheetService = tripSheetService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public JsonVehicleRoutingSolution genrateTripSheetData(@RequestBody TripSheet tripSheet) {
		tripSheetService.terminateEarly(tripSheet);
		VehicleRoutingSolution solution = tripSheetService.retrieveOrPrepareTripSheetData(tripSheet);
		
		return tripSheetService.generateTripSheet(tripSheet, solution);
	}

	@RequestMapping(method = RequestMethod.POST, value = "solve")
	public BaseDTO solve(@RequestBody TripSheet tripSheet) {
		BaseDTO baseDTO = new BaseDTO();
		int numberOfEmployees = getNoOfEmploees(tripSheet);
		int capacity = 0;
		for(Vehicle vehicle: tripSheet.getVehicles()) {
			capacity = capacity + vehicle.getSeats();
		}
		
		if(numberOfEmployees>capacity) {
			baseDTO.setSuccess(false);
			baseDTO.setStatus("Vehicle seats or capacity is not enough to generate tripsheet.");
		} else {
			tripSheetService.retrieveOrPrepareTripSheetData(tripSheet);
			boolean isSuccess = tripSheetService.solveRoute(tripSheet);
			baseDTO.setSuccess(isSuccess);
		}
		return baseDTO;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public JsonVehicleRoutingSolution save(@RequestBody JsonVehicleRoutingSolution solution) {
		tripSheetService.saveTripSheet(solution);
		return solution;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "get")
	public JsonVehicleRoutingSolution get(@RequestBody TripSheet tripSheet) {
		return tripSheetService.getTripSheet(tripSheet);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "count")
	public int getNoOfEmploees(@RequestBody TripSheet tripSheet) {
		List<PickupPoint> pickUpPoints =tripSheetService.getPickupPointDetails(tripSheet);
		int numberOfEmployees = 0;
		for(PickupPoint pickUpPoint: pickUpPoints) {
			numberOfEmployees = numberOfEmployees + pickUpPoint.getNumberOfEmployees();
		}
		return numberOfEmployees;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "findCab")
	public Vehicle findCab(@RequestBody TripSheet tripSheet, HttpServletRequest request) {
		Employee employee = (Employee) request.getSession().getAttribute("loggedInUser");
		Vehicle vehicle = tripSheetService.findCab(tripSheet, employee.getId());
		
		if(vehicle == null) {
			vehicle = new Vehicle();
			vehicle.setStatus("Not found");
		}
		
		return vehicle;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "export")
	protected ModelAndView export(@RequestParam(value="date") String string, 
			@RequestParam(value="id") Long id, @RequestParam(value="isDrop") boolean isDrop) throws Exception {
		
		Date date = new Date(Long.parseLong(string));
		
		TripSheet tripSheet = new TripSheet();
		tripSheet.setDate(date);
		tripSheet.setShiftId(id);
		tripSheet.setDrop(isDrop);;
		List<TripRoute> tripRoutes = tripSheetService.getTripSheetDataForExport(tripSheet);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(new ExcelTripSheetReportView());
		mav.addObject("tripRoutes", tripRoutes);
		mav.addObject("tripSheetReq", tripSheet);
		return mav;	
	}
}