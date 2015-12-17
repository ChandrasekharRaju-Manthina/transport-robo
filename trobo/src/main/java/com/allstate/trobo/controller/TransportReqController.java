package com.allstate.trobo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(method = RequestMethod.POST)
	public TransportReq addTransportReq(@Valid @RequestBody TransportReq transportReq) {
		return transportReqService.addTransportReq(transportReq);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public TransportReq updateTransportReq(@Valid @RequestBody TransportReq transportReq) {
		return transportReqService.updateTransportReq(transportReq);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void deleteTransportReq(@PathVariable Long id) {
		System.out.println(id);
		transportReqService.deleteTransportReq(id);
	}
}