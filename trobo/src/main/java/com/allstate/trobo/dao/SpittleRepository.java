package com.allstate.trobo.dao;

import java.util.List;

import com.allstate.trobo.domain.Spittle;

public interface SpittleRepository {
	List<Spittle> findSpittles(long max, int count);
}