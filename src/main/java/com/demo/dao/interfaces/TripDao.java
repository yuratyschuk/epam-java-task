package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Trip;

import java.util.List;

public interface TripDao extends DAO<Trip> {

    List<Trip> getByRouteId(int routeId);
}
