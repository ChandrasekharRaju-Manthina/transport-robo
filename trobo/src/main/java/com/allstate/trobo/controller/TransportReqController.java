package com.allstate.trobo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allstate.trobo.domain.Employee;
import com.allstate.trobo.domain.TransportReq;
import com.allstate.trobo.service.TransportReqService;

@RestController
@RequestMapping("/transportReqs")
public class TransportReqController {

	TransportReqService transportReqService;

	@Autowired
	public TransportReqController(TransportReqService transportReqService) {
		this.transportReqService = transportReqService;
	}

	@RequestMapping
	public List<TransportReq> getTransportReqs() {
		return transportReqService.getAllTransportReqs();
	}
	
	@RequestMapping(value="employee")
	public List<TransportReq> getEmpTransportReqs(HttpServletRequest request) {
		Employee employee = (Employee) request.getSession().getAttribute("loggedInUser");
		return transportReqService.getTransportReqs(employee.getId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public TransportReq addTransportReq(@RequestBody TransportReq transportReq, HttpServletRequest request) {
		Employee employee = (Employee) request.getSession().getAttribute("loggedInUser");
		transportReq.setEmployeeId(employee.getId());
		return transportReqService.addTransportReq(transportReq);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public TransportReq updateTransportReq(@RequestBody TransportReq transportReq, HttpServletRequest request) {
		Employee employee = (Employee) request.getSession().getAttribute("loggedInUser");
		transportReq.setEmployeeId(employee.getId());
		return transportReqService.updateTransportReq(transportReq);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteTransportReq(@PathVariable Long id) {
		transportReqService.deleteTransportReq(id);
	}
}