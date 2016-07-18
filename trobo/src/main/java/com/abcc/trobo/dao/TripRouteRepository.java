package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.TripRoute;
import com.allstate.trobo.domain.Vehicle;

public interface TripRouteRepository {

	List<TripRoute> save(List<TripRoute> tripRoutes);

	List<TripRoute> get(String tripSheetId);

	Vehicle findCab(String tripSheetId, Long empId);

}
