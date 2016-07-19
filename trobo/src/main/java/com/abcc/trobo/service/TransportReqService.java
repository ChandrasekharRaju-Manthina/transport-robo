package com.abcc.trobo.service;

import java.util.List;

import com.abcc.trobo.domain.TransportReq;

public interface TransportReqService {
	public TransportReq addTransportReq(TransportReq transportReq);

	public List<TransportReq> getAllTransportReqs();

	public void deleteTransportReq(Long id);

	public TransportReq updateTransportReq(TransportReq transportReq);

	List<TransportReq> getTransportReqs(Long id);

}
