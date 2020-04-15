package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Route;

public interface RouteDao extends DAO<Route> {

    public Route getByArrivalPlaceIdAndDeparturePlaceId(Integer departurePlaceId, Integer arrivalPlaceId);
}
