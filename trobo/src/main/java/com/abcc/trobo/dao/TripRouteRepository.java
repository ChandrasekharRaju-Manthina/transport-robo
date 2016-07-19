package com.abcc.trobo.dao;

import java.util.List;

import com.abcc.trobo.domain.TripRoute;
import com.abcc.trobo.domain.Vehicle;

public interface TripRouteRepository {

	List<TripRoute> save(List<TripRoute> tripRoutes);

	List<TripRoute> get(String tripSheetId);

	Vehicle findCab(String tripSheetId, Long empId);

}
