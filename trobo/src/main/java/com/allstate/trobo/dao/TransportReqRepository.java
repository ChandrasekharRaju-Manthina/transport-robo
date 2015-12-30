package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.TransportReq;

public interface TransportReqRepository {

	public TransportReq save(TransportReq trasnportReq);

	public List<TransportReq> retrieveAll();

	public void delete(Long id);

	TransportReq update(TransportReq trasnportReq);

	List<TransportReq> retrieve(Long id);

}
