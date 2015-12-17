package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.PickupPoint;


public interface TripSheetRepository {
	public List<PickupPoint> getPickUpPointDetails();
}
