package com.abcc.trobo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcc.trobo.dao.TransportReqRepository;
import com.abcc.trobo.domain.TransportReq;
import com.abcc.trobo.service.TransportReqService;

@Service
public class TransportReqServiceImpl implements TransportReqService {

	private TransportReqRepository transportReqRepository;

	@Autowired
	public TransportReqServiceImpl(TransportReqRepository transportReqRepository) {
		this.transportReqRepository = transportReqRepository;
	}

	@Override
	public TransportReq updateTransportReq(TransportReq transportReq) {
		return transportReqRepository.update(transportReq);
	}

	@Override
	public TransportReq addTransportReq(TransportReq transportReq) {
		transportReq.setStatus("A");
		return transportReqRepository.save(transportReq);
	}

	@Override
	public void deleteTransportReq(Long id) {
		transportReqRepository.delete(id);
	}

	@Override
	public List<TransportReq> getAllTransportReqs() {
		return transportReqRepository.retrieveAll();
	}
	
	@Override
	public List<TransportReq> getTransportReqs(Long id) {
		return transportReqRepository.retrieve(id);
	}

}
